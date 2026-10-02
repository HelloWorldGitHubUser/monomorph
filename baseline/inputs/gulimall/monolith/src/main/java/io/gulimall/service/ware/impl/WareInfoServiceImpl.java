package io.gulimall.service.ware.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gulimall.utils.PageUtils;
import io.gulimall.utils.Query;
import io.gulimall.entity.member.MemberReceiveAddressEntity;
import io.gulimall.service.member.MemberReceiveAddressService;
import io.gulimall.dao.ware.WareInfoDao;
import io.gulimall.entity.ware.WareInfoEntity;
import io.gulimall.service.ware.WareInfoService;
import io.gulimall.vo.FareVo;
import io.gulimall.vo.MemberAddressVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                new QueryWrapper<WareInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 计算运费：单库模式下直接查询地址表
     */
    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        MemberReceiveAddressEntity entity = memberReceiveAddressService.getById(addrId);
        if (entity != null) {
            MemberAddressVo addressVo = new MemberAddressVo();
            BeanUtils.copyProperties(entity, addressVo);
            fareVo.setAddress(addressVo);
            String phone = addressVo.getPhone();
            if (phone != null && phone.length() >= 2) {
                String fare = phone.substring(phone.length() - 2);
                fareVo.setFare(new BigDecimal(fare));
            } else {
                fareVo.setFare(BigDecimal.ZERO);
            }
        }
        return fareVo;
    }

}