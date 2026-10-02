

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.orderstatusenum.OrderStatusEnumDTO;
import com.goodskill.enums.OrderStatusEnum;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link OrderStatusEnum} and {@link OrderStatusEnumDTO}.
 */
@Mapper(componentModel = "default") 
public interface OrderStatusEnumMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    OrderStatusEnumMapper INSTANCE = Mappers.getMapper(OrderStatusEnumMapper.class);

    /**
     * Maps from {@link OrderStatusEnumDTO} to {@link OrderStatusEnum}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link OrderStatusEnum} object.
     */
    OrderStatusEnum fromDTO(OrderStatusEnumDTO dto);

    /**
     * Maps from {@link OrderStatusEnum} to {@link OrderStatusEnumDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link OrderStatusEnumDTO} object.
     */
    OrderStatusEnumDTO toDTO(OrderStatusEnum domain);

}