

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;
import com.youlai.mall.model.ums.dto.MemberAddressDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link MemberAddressDTO} and {@link MemberAddressDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface MemberAddressDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    MemberAddressDTOMapper INSTANCE = Mappers.getMapper(MemberAddressDTOMapper.class);

    /**
     * Maps from {@link MemberAddressDTODTO} to {@link MemberAddressDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link MemberAddressDTO} object.
     */
    MemberAddressDTO fromDTO(MemberAddressDTODTO dto);

    /**
     * Maps from {@link MemberAddressDTO} to {@link MemberAddressDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link MemberAddressDTODTO} object.
     */
    MemberAddressDTODTO toDTO(MemberAddressDTO domain);

}