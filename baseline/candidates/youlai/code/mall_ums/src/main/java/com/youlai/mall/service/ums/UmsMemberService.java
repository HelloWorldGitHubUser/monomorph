package com.youlai.mall.service.ums;
import com.youlai.mall.model.ums.dto.MemberAddressDTO;
import com.youlai.mall.model.ums.dto.MemberAuthDTO;
import com.youlai.mall.model.ums.dto.MemberRegisterDto;
import com.youlai.mall.model.ums.entity.UmsMember;
import com.youlai.mall.model.ums.vo.MemberVO;
import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO;
import java.util.List;
import java.util.Set;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 会员业务接口
 *
 * @author haoxr
 * @since 2022/2/12
 */
public interface UmsMemberService extends IService<UmsMember> {
    IPage<UmsMember> list(Page<UmsMember> page, String nickname);

    void addProductViewHistory(ProductHistoryVO product, Long userId);

    Set<ProductHistoryVO> getProductViewHistory(Long userId);

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return  */
    MemberAuthDTO getMemberByOpenid(String openid);

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return  */
    MemberAuthDTO getMemberByMobile(String mobile);

    /**
     * 新增会员
     *
     * @param member
     * @return  */
    Long addMember(MemberRegisterDto member);

    /**
     * 获取登录会员信息
     *
     * @return  */
    MemberVO getCurrMemberInfo();

    /**
     * 获取会员地址列表
     *
     * @param memberId
     * @return  */
    List<MemberAddressDTO> listMemberAddress(Long memberId);

    /**
     * 获取会员地址列表（别名方法）
     */
    default List<MemberAddressDTO> listMemberAddresses(Long memberId) {
        return listMemberAddress(memberId);
    }

    /**
     * 扣减会员余额
     *
     * @param memberId
     * 		会员ID
     * @param amount
     * 		扣减金额（单位：分）
     */
    void deductBalance(Long memberId, Long amount);

    /**
     * 获取会员的OpenID
     *
     * @param memberId
     * 		会员ID
     * @return OpenID
     */
    String getMemberOpenId(Long memberId);

    /**
     * 添加商品浏览记录（重载方法）
     */
    default void addProductViewHistory(ProductHistoryVO vo) {
        // 从SecurityUtils获取当前用户ID
        addProductViewHistory(vo, null);
    }

    /**
     * 根据手机号获取会员认证信息（别名方法）
     */
    default MemberAuthDTO loadUserByMobile(String mobile) {
        return getMemberByMobile(mobile);
    }

    /**
     * 根据OpenID获取会员认证信息（别名方法）
     */
    default MemberAuthDTO loadUserByOpenId(String openid) {
        return getMemberByOpenid(openid);
    }

    /**
     * 注册会员（别名方法）
     */
    default Long registerMember(MemberRegisterDto dto) {
        return addMember(dto);
    }
}