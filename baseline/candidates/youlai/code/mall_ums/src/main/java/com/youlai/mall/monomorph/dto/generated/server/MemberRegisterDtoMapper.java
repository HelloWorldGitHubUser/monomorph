

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import com.youlai.mall.model.ums.dto.MemberRegisterDto;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link MemberRegisterDto} and {@link MemberRegisterDtoDTO}.
 */
@Mapper(componentModel = "default") 
public interface MemberRegisterDtoMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    MemberRegisterDtoMapper INSTANCE = Mappers.getMapper(MemberRegisterDtoMapper.class);

    /**
     * Maps from {@link MemberRegisterDtoDTO} to {@link MemberRegisterDto}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link MemberRegisterDto} object.
     */
    MemberRegisterDto fromDTO(MemberRegisterDtoDTO dto);

    /**
     * Maps from {@link MemberRegisterDto} to {@link MemberRegisterDtoDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link MemberRegisterDtoDTO} object.
     */
    MemberRegisterDtoDTO toDTO(MemberRegisterDto domain);

}