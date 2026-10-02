

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.userauthinfo.UserAuthInfoDTO;
import com.youlai.mall.model.system.dto.UserAuthInfo;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link UserAuthInfo} and {@link UserAuthInfoDTO}.
 */
@Mapper(componentModel = "default") 
public interface UserAuthInfoMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    UserAuthInfoMapper INSTANCE = Mappers.getMapper(UserAuthInfoMapper.class);

    /**
     * Maps from {@link UserAuthInfoDTO} to {@link UserAuthInfo}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link UserAuthInfo} object.
     */
    UserAuthInfo fromDTO(UserAuthInfoDTO dto);

    /**
     * Maps from {@link UserAuthInfo} to {@link UserAuthInfoDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link UserAuthInfoDTO} object.
     */
    UserAuthInfoDTO toDTO(UserAuthInfo domain);

}