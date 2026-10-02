

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.orderdto.OrderDTODTO;
import com.goodskill.dto.OrderDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link OrderDTO} and {@link OrderDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface OrderDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    OrderDTOMapper INSTANCE = Mappers.getMapper(OrderDTOMapper.class);

    /**
     * Maps from {@link OrderDTODTO} to {@link OrderDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link OrderDTO} object.
     */
    OrderDTO fromDTO(OrderDTODTO dto);

    /**
     * Maps from {@link OrderDTO} to {@link OrderDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link OrderDTODTO} object.
     */
    OrderDTODTO toDTO(OrderDTO domain);

}