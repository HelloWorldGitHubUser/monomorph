

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.systemconstants.SystemConstantsDTO;
import com.youlai.mall.constant.SystemConstants;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SystemConstants} and {@link SystemConstantsDTO}.
 */
@Mapper(componentModel = "default") 
public interface SystemConstantsMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SystemConstantsMapper INSTANCE = Mappers.getMapper(SystemConstantsMapper.class);

    /**
     * Maps from {@link SystemConstantsDTO} to {@link SystemConstants}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SystemConstants} object.
     */
    SystemConstants fromDTO(SystemConstantsDTO dto);

    /**
     * Maps from {@link SystemConstants} to {@link SystemConstantsDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SystemConstantsDTO} object.
     */
    SystemConstantsDTO toDTO(SystemConstants domain);

}