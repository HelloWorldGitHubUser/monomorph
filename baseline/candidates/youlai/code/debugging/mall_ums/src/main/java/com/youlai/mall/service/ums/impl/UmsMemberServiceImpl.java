package com.youlai.mall.service.ums.impl;
import com.youlai.mall.constant.MemberConstants;
import com.youlai.mall.converter.AddressConvert;
import com.youlai.mall.converter.MemberConvert;
import com.youlai.mall.mapper.UmsMemberMapper;
import com.youlai.mall.model.ums.dto.MemberAddressDTO;
import com.youlai.mall.model.ums.dto.MemberAuthDTO;
import com.youlai.mall.model.ums.dto.MemberRegisterDto;
import com.youlai.mall.model.ums.entity.UmsAddress;
import com.youlai.mall.model.ums.entity.UmsMember;
import com.youlai.mall.model.ums.vo.MemberVO;
import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO;
import com.youlai.mall.result.ResultCode;
import com.youlai.mall.security.util.SecurityUtils;
import com.youlai.mall.service.ums.UmsAddressService;
import com.youlai.mall.service.ums.UmsMemberService;
import com.youlai.mall.web.exception.BizException;
import java.util.List;
import java.util.Set;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * 会员业务实现类
 *
 * @author haoxr
 * @since 2022/2/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    private final RedisTemplate redisTemplate;

    private final MemberConvert memberConvert;

    private final AddressConvert addressConvert;

    private final UmsAddressService addressService;

    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, String nickname) {
        List<UmsMember> list = this.baseMapper.list(page, nickname);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = MemberConstants.USER_PRODUCT_HISTORY + userId;
            redisTemplate.opsForZSet().add(key, product, System.currentTimeMillis());
            Long size = redisTemplate.opsForZSet().size(key);
            if (size > 10) {
                redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        return redisTemplate.opsForZSet().reverseRange(MemberConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
    }

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * 		微信唯一身份标识
     * @return  */
    @Override
    public MemberAuthDTO getMemberByOpenid(String openid) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>().eq(UmsMember::getOpenid, openid).select(UmsMember::getId, UmsMember::getOpenid, UmsMember::getStatus));
        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2OpenidAuthDTO(entity);
    }

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return  */
    @Override
    public MemberAuthDTO getMemberByMobile(String mobile) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>().eq(UmsMember::getMobile, mobile).select(UmsMember::getId, UmsMember::getMobile, UmsMember::getStatus));
        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2MobileAuthDTO(entity);
    }

    /**
     * 新增会员
     *
     * @param memberRegisterDTO
     * @return  */
    @Override
    public Long addMember(MemberRegisterDto memberRegisterDTO) {
        UmsMember umsMember = memberConvert.dto2Entity(memberRegisterDTO);
        boolean result = this.save(umsMember);
        Assert.isTrue(result, "新增会员失败");
        return umsMember.getId();
    }

    /**
     * 获取登录会员信息
     *
     * @return  */
    @Override
    public MemberVO getCurrMemberInfo() {
        Long memberId = SecurityUtils.getMemberId();
        UmsMember umsMember = this.getOne(new LambdaQueryWrapper<UmsMember>().eq(UmsMember::getId, memberId).select(UmsMember::getId, UmsMember::getNickName, UmsMember::getAvatarUrl, UmsMember::getMobile, UmsMember::getBalance));
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(umsMember, memberVO);
        return memberVO;
    }

    /**
     * 获取会员地址
     *
     * @param memberId
     * @return  */
    @Override
    public List<MemberAddressDTO> listMemberAddress(Long memberId) {
        List<UmsAddress> entities = addressService.list(new LambdaQueryWrapper<UmsAddress>().eq(UmsAddress::getMemberId, memberId));
        List<MemberAddressDTO> list = addressConvert.entity2Dto(entities);
        return list;
    }

    /**
     * 扣减会员余额
     *
     * @param memberId
     * 		会员ID
     * @param amount
     * 		扣减金额（单位：分）
     */
    @Override
    public void deductBalance(Long memberId, Long amount) {
        this.update(new LambdaUpdateWrapper<UmsMember>().setSql("balance = balance - " + amount).eq(UmsMember::getId, memberId));
    }

    /**
     * 获取会员的OpenID
     *
     * @param memberId
     * 		会员ID
     * @return OpenID
     */
    @Override
    public String getMemberOpenId(Long memberId) {
        UmsMember member = this.getOne(new LambdaQueryWrapper<UmsMember>().eq(UmsMember::getId, memberId).select(UmsMember::getOpenid));
        String openid = member.getOpenid();
        return openid;
    }
}