package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.customerid.CustomerIdDTO;

/**
 * Auto-generated DTO gRPC client
 * {@link CustomerId} and {@link CustomerIdDTO}.
 */
public class CustomerId {

    private CustomerIdDTO dtoInstance;

    public CustomerId(CustomerIdDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public CustomerId() {
        this(CustomerIdDTO.newBuilder().build());
    }

    public CustomerId(String id) {
        this();
        setId(id);
    }

    public CustomerIdDTO toDTO() {
        return this.dtoInstance;
    }

    public static CustomerId fromDTO(CustomerIdDTO dtoInstance) {
        return new CustomerId(dtoInstance);
    }

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
}
