package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.customerid.*;

public class CustomerId {
    private CustomerIdDTO dtoInstance;

    public CustomerId() {
        this.dtoInstance = new CustomerIdDTO();
        this.dtoInstance.setSerialVersionUID(1L);
    }

    public CustomerId(String id) {
        this();
        setId(id);
    }

    public CustomerId(CustomerIdDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
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
        dtoInstance.setId(id == null ? "" : id);
    }

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUID();
    }

    public void setSerialVersionUID(long serialVersionUID) {
        dtoInstance.setSerialVersionUID(serialVersionUID);
    }
}