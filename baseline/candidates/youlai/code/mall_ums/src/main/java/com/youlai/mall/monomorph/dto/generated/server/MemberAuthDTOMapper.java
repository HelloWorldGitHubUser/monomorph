

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTODTO;
import com.youlai.mall.model.ums.dto.MemberAuthDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link MemberAuthDTO} and {@link MemberAuthDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface MemberAuthDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    MemberAuthDTOMapper INSTANCE = Mappers.getMapper(MemberAuthDTOMapper.class);

    /**
     * Maps from {@link MemberAuthDTODTO} to {@link MemberAuthDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link MemberAuthDTO} object.
     */
    MemberAuthDTO fromDTO(MemberAuthDTODTO dto);

    /**
     * Maps from {@link MemberAuthDTO} to {@link MemberAuthDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link MemberAuthDTODTO} object.
     */
    MemberAuthDTODTO toDTO(MemberAuthDTO domain);

}