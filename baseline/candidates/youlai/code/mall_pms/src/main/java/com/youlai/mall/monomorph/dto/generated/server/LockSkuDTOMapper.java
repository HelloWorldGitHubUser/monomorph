

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.lockskudto.LockSkuDTODTO;
import com.youlai.mall.model.pms.dto.LockSkuDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link LockSkuDTO} and {@link LockSkuDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface LockSkuDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    LockSkuDTOMapper INSTANCE = Mappers.getMapper(LockSkuDTOMapper.class);

    /**
     * Maps from {@link LockSkuDTODTO} to {@link LockSkuDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link LockSkuDTO} object.
     */
    LockSkuDTO fromDTO(LockSkuDTODTO dto);

    /**
     * Maps from {@link LockSkuDTO} to {@link LockSkuDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link LockSkuDTODTO} object.
     */
    LockSkuDTODTO toDTO(LockSkuDTO domain);

}