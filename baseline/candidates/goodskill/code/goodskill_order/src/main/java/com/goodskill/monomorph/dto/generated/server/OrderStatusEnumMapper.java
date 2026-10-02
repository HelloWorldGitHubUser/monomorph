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
    default OrderStatusEnum fromDTO(OrderStatusEnumDTO dto) {
        if (dto == null) {
            return null;
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            return OrderStatusEnum.valueOf(dto.getName());
        }
        return OrderStatusEnum.getByCode((byte) dto.getCode());
    }

    /**
     * Maps from {@link OrderStatusEnum} to {@link OrderStatusEnumDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link OrderStatusEnumDTO} object.
     */
    default OrderStatusEnumDTO toDTO(OrderStatusEnum domain) {
        if (domain == null) {
            return null;
        }
        return OrderStatusEnumDTO.newBuilder()
                .setCode(domain.getCode())
                .setDesc(domain.getDesc())
                .setName(domain.name())
                .build();
    }
}

