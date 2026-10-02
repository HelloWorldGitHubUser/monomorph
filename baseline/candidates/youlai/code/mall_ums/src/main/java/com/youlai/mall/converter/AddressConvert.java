package com.youlai.mall.converter;

import com.youlai.mall.model.ums.dto.MemberAddressDTO;
import com.youlai.mall.model.ums.entity.UmsAddress;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 会员地址对象转换器
 *
 * @author haoxr
 * @since 2022/6/21 23:52
 */
@Mapper(componentModel = "spring")
public interface AddressConvert {

    MemberAddressDTO entity2Dto(UmsAddress entity);

    List<MemberAddressDTO> entity2Dto(List<UmsAddress> entities);
}









