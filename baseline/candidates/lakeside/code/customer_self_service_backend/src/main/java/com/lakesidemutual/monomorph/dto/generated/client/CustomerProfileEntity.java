package com.lakesidemutual.monomorph.dto.generated.client;

import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.*;
import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;
import com.lakesidemutual.monomorph.dto.generated.client.Address;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client for {@link CustomerProfileEntity}.
 * Uses composition over {@link CustomerProfileEntityDTO}.
 */
public class CustomerProfileEntity {
    private CustomerProfileEntityDTO dtoInstance;

    public CustomerProfileEntity(CustomerProfileEntityDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public CustomerProfileEntity() {
        this.dtoInstance = CustomerProfileEntityDTO.newBuilder().build();
    }

    public CustomerProfileEntity(String firstname, String lastname, Date birthday,
                                 Address currentAddress, String email, String phoneNumber) {
        CustomerProfileEntityDTO.Builder builder = CustomerProfileEntityDTO.newBuilder();
        if (firstname != null) builder.setFirstname(firstname);
        if (lastname != null) builder.setLastname(lastname);
        if (birthday != null) {
            Instant instant = Instant.ofEpochMilli(birthday.getTime());
            builder.setBirthday(Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build());
        }
        if (currentAddress != null) builder.setCurrentAddress(currentAddress.toDTO());
        if (email != null) builder.setEmail(email);
        if (phoneNumber != null) builder.setPhoneNumber(phoneNumber);
        this.dtoInstance = builder.build();
    }

    public CustomerProfileEntityDTO toDTO() {
        return this.dtoInstance;
    }

    public static CustomerProfileEntity fromDTO(CustomerProfileEntityDTO dtoInstance) {
        return new CustomerProfileEntity(dtoInstance);
    }

    // DTO getters and setters

    public long getCustomerId() {
        return dtoInstance.getCustomerId();
    }

    public void setCustomerId(long customerId) {
        dtoInstance = dtoInstance.toBuilder().setCustomerId(customerId).build();
    }

    public String getFirstname() {
        return dtoInstance.getFirstname();
    }

    public void setFirstname(String firstname) {
        dtoInstance = dtoInstance.toBuilder().setFirstname(firstname == null ? "" : firstname).build();
    }

    public String getLastname() {
        return dtoInstance.getLastname();
    }

    public void setLastname(String lastname) {
        dtoInstance = dtoInstance.toBuilder().setLastname(lastname == null ? "" : lastname).build();
    }

    public Date getBirthday() {
        if (!dtoInstance.hasBirthday()) {
            return null;
        }
        Timestamp ts = dtoInstance.getBirthday();
        return Date.from(Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos()));
    }

    public void setBirthday(Date birthday) {
        if (birthday == null) {
            dtoInstance = dtoInstance.toBuilder().clearBirthday().build();
            return;
        }
        Instant instant = Instant.ofEpochMilli(birthday.getTime());
        Timestamp ts = Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
        dtoInstance = dtoInstance.toBuilder().setBirthday(ts).build();
    }

    public Address getCurrentAddress() {
        if (!dtoInstance.hasCurrentAddress()) {
            return null;
        }
        return Address.fromDTO(dtoInstance.getCurrentAddress());
    }

    public void setCurrentAddress(Address currentAddress) {
        if (currentAddress == null) {
            dtoInstance = dtoInstance.toBuilder().clearCurrentAddress().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setCurrentAddress(currentAddress.toDTO()).build();
        }
    }

    public String getEmail() {
        return dtoInstance.getEmail();
    }

    public void setEmail(String email) {
        dtoInstance = dtoInstance.toBuilder().setEmail(email == null ? "" : email).build();
    }

    public String getPhoneNumber() {
        return dtoInstance.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        dtoInstance = dtoInstance.toBuilder().setPhoneNumber(phoneNumber == null ? "" : phoneNumber).build();
    }

    public Collection<Address> getMoveHistory() {
        Collection<Address> result = new ArrayList<>();
        for (AddressDTO addressDTO : dtoInstance.getMoveHistoryList()) {
            result.add(Address.fromDTO(addressDTO));
        }
        return result;
    }

    public void setMoveHistory(Collection<Address> moveHistory) {
        CustomerProfileEntityDTO.Builder builder = dtoInstance.toBuilder();
        builder.clearMoveHistory();
        if (moveHistory != null) {
            for (Address address : moveHistory) {
                builder.addMoveHistory(address.toDTO());
            }
        }
        dtoInstance = builder.build();
    }
}