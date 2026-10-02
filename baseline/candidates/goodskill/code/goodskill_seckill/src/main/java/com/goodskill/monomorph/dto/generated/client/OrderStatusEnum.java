package com.goodskill.monomorph.dto.generated.client;

import com.goodskill.monomorph.dto.generated.proto.orderstatusenum.OrderStatusEnumDTO;

import java.util.Objects;

/**
 * Auto-generated DTO client for OrderStatusEnum.
 *
 * <p>Uses composition to store data in an {@link OrderStatusEnumDTO} and exposes
 * the same field-level API as the original enum through getters and setters.
 * The protobuf DTO contains no service methods, so this client has no gRPC call logic.</p>
 */
public class OrderStatusEnum {

    private OrderStatusEnumDTO dtoInstance;

    /**
     * DTO constructor. Maintained to support {@link #fromDTO(OrderStatusEnumDTO)}.
     */
    public OrderStatusEnum(OrderStatusEnumDTO dtoInstance) {
        this.dtoInstance = Objects.requireNonNull(dtoInstance, "dtoInstance");
    }

    public OrderStatusEnumDTO toDTO() {
        return this.dtoInstance;
    }

    public static OrderStatusEnum fromDTO(OrderStatusEnumDTO dtoInstance) {
        return new OrderStatusEnum(dtoInstance);
    }

    // --- DTO field getters and setters ---

    /**
     * Returns the status code as a {@link Byte} to match the original API.
     * The underlying DTO stores the value as protobuf {@code int32}.
     */
    public Byte getCode() {
        return (byte) dtoInstance.getCode();
    }

    public void setCode(Byte code) {
        dtoInstance = dtoInstance.toBuilder()
                .setCode(code == null ? 0 : code.intValue())
                .build();
    }

    public String getDesc() {
        return dtoInstance.getDesc();
    }

    public void setDesc(String desc) {
        dtoInstance = dtoInstance.toBuilder()
                .setDesc(desc == null ? "" : desc)
                .build();
    }

    public String getName() {
        return dtoInstance.getName();
    }

    public void setName(String name) {
        dtoInstance = dtoInstance.toBuilder()
                .setName(name == null ? "" : name)
                .build();
    }

    /**
     * Enum-style name accessor, delegates to the DTO {@code name} field.
     */
    public String name() {
        return dtoInstance.getName();
    }
}