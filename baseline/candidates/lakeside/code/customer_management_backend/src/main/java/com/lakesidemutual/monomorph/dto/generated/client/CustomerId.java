package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.customerid.CustomerIdDTO;

import java.util.Objects;

/**
 * Auto-generated DTO gRPC client for {@code CustomerIdDTO}.
 *
 * <p>Since the proto definition contains only a message and no service,
 * this client is a pure DTO composition wrapper around {@link CustomerIdDTO}.
 */
public class CustomerId {
    private CustomerIdDTO dtoInstance;

    public CustomerId(CustomerIdDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null
                ? CustomerIdDTO.newBuilder()
                    .setId("")
                    .setSerialVersionUID(1L)
                    .build()
                : dtoInstance;
    }

    public CustomerId() {
        this.dtoInstance = CustomerIdDTO.newBuilder()
                .setId("")
                .setSerialVersionUID(1L)
                .build();
    }

    public CustomerId(String id) {
        this();
        this.setId(id);
    }

    // DTO mapping methods
    public CustomerIdDTO toDTO() {
        return this.dtoInstance;
    }

    public static CustomerId fromDTO(CustomerIdDTO dtoInstance) {
        return new CustomerId(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---
    public String getId() {
        return dtoInstance.getId();
    }

    public void setId(String id) {
        dtoInstance = dtoInstance.toBuilder()
                .setId(id == null ? "" : id)
                .build();
    }

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUID();
    }

    public void setSerialVersionUID(long serialVersionUID) {
        dtoInstance = dtoInstance.toBuilder()
                .setSerialVersionUID(serialVersionUID)
                .build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CustomerId other = (CustomerId) obj;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return getId();
    }
}
