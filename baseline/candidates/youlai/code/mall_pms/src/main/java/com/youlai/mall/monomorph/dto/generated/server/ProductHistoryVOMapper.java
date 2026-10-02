

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.ProductHistoryVODTO;
import com.youlai.mall.model.pms.vo.ProductHistoryVO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link ProductHistoryVO} and {@link ProductHistoryVODTO}.
 */
@Mapper(componentModel = "default") 
public interface ProductHistoryVOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    ProductHistoryVOMapper INSTANCE = Mappers.getMapper(ProductHistoryVOMapper.class);

    /**
     * Maps from {@link ProductHistoryVODTO} to {@link ProductHistoryVO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link ProductHistoryVO} object.
     */
    ProductHistoryVO fromDTO(ProductHistoryVODTO dto);

    /**
     * Maps from {@link ProductHistoryVO} to {@link ProductHistoryVODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link ProductHistoryVODTO} object.
     */
    ProductHistoryVODTO toDTO(ProductHistoryVO domain);

}