package com.youlai.mall.service.ums;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.model.ums.dto.MemberAddressDTO;
import com.youlai.mall.model.ums.entity.UmsAddress;
import com.youlai.mall.model.ums.form.AddressForm;

import java.util.List;

/**
 * 会员地址业务接口
 *
 * @author haoxr
 * @since 2022/2/12
 */
public interface UmsAddressService extends IService<UmsAddress> {

    /**
     * 新增地址
     *
     * @param addressForm
     * @return
     */
    boolean addAddress(AddressForm addressForm);

    /**
     * 修改地址
     *
     * @param addressForm
     * @return
     */
    boolean updateAddress(AddressForm addressForm);

    /**
     * 获取当前登录会员的地址列表
     *
     * @return
     */
    List<MemberAddressDTO> listCurrentMemberAddresses();
}
