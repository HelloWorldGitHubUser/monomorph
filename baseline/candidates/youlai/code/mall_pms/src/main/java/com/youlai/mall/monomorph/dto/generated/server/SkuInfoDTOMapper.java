

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.skuinfodto.SkuInfoDTODTO;
import com.youlai.mall.model.pms.dto.SkuInfoDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SkuInfoDTO} and {@link SkuInfoDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface SkuInfoDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SkuInfoDTOMapper INSTANCE = Mappers.getMapper(SkuInfoDTOMapper.class);

    /**
     * Maps from {@link SkuInfoDTODTO} to {@link SkuInfoDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SkuInfoDTO} object.
     */
    SkuInfoDTO fromDTO(SkuInfoDTODTO dto);

    /**
     * Maps from {@link SkuInfoDTO} to {@link SkuInfoDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SkuInfoDTODTO} object.
     */
    SkuInfoDTODTO toDTO(SkuInfoDTO domain);

}