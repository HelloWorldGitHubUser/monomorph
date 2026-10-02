package com.lakesidemutual.monomorph.dto.generated.client;

import com.google.protobuf.Timestamp;
import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.CustomerProfileEntityDTO;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client for CustomerProfileEntity.
 * Uses composition over CustomerProfileEntityDTO.
 */
public class CustomerProfileEntity {

    private CustomerProfileEntityDTO dtoInstance;

    public CustomerProfileEntity() {
        this.dtoInstance = CustomerProfileEntityDTO.newBuilder().build();
    }

    public CustomerProfileEntity(
            String firstname,
            String lastname,
            Date birthday,
            Address currentAddress,
            String email,
            String phoneNumber) {

        CustomerProfileEntityDTO.Builder builder = CustomerProfileEntityDTO.newBuilder();

        if (firstname != null) {
            builder.setFirstname(firstname);
        }
        if (lastname != null) {
            builder.setLastname(lastname);
        }
        if (birthday != null) {
            Instant instant = birthday.toInstant();
            builder.setBirthday(
                    Timestamp.newBuilder()
                            .setSeconds(instant.getEpochSecond())
                            .setNanos(instant.getNano())
                            .build());
        }
        if (currentAddress != null) {
            builder.setCurrentAddress(currentAddress.toDTO());
        }
        if (email != null) {
            builder.setEmail(email);
        }
        if (phoneNumber != null) {
            builder.setPhoneNumber(phoneNumber);
        }

        this.dtoInstance = builder.build();
    }

    private CustomerProfileEntity(CustomerProfileEntityDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public CustomerProfileEntityDTO toDTO() {
        return this.dtoInstance;
    }

    public static CustomerProfileEntity fromDTO(CustomerProfileEntityDTO dtoInstance) {
        return new CustomerProfileEntity(dtoInstance);
    }

    public long getCustomerId() {
        return dtoInstance.getCustomerId();
    }

    public void setCustomerId(long customerId) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setCustomerId(customerId)
                .build();
    }

    public String getFirstname() {
        return dtoInstance.getFirstname();
    }

    public void setFirstname(String firstname) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (firstname == null) {
            builder.clearFirstname();
        } else {
            builder.setFirstname(firstname);
        }
        this.dtoInstance = builder.build();
    }

    public String getLastname() {
        return dtoInstance.getLastname();
    }

    public void setLastname(String lastname) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (lastname == null) {
            builder.clearLastname();
        } else {
            builder.setLastname(lastname);
        }
        this.dtoInstance = builder.build();
    }

    public Date getBirthday() {
        if (dtoInstance.hasBirthday()) {
            Timestamp timestamp = dtoInstance.getBirthday();
            return Date.from(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()));
        }
        return null;
    }

    public void setBirthday(Date birthday) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (birthday == null) {
            builder.clearBirthday();
        } else {
            Instant instant = birthday.toInstant();
            builder.setBirthday(
                    Timestamp.newBuilder()
                            .setSeconds(instant.getEpochSecond())
                            .setNanos(instant.getNano())
                            .build());
        }
        this.dtoInstance = builder.build();
    }

    public Address getCurrentAddress() {
        if (dtoInstance.hasCurrentAddress()) {
            return Address.fromDTO(dtoInstance.getCurrentAddress());
        }
        return null;
    }

    public void setCurrentAddress(Address currentAddress) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (currentAddress == null) {
            builder.clearCurrentAddress();
        } else {
            builder.setCurrentAddress(currentAddress.toDTO());
        }
        this.dtoInstance = builder.build();
    }

    public String getEmail() {
        return dtoInstance.getEmail();
    }

    public void setEmail(String email) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (email == null) {
            builder.clearEmail();
        } else {
            builder.setEmail(email);
        }
        this.dtoInstance = builder.build();
    }

    public String getPhoneNumber() {
        return dtoInstance.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (phoneNumber == null) {
            builder.clearPhoneNumber();
        } else {
            builder.setPhoneNumber(phoneNumber);
        }
        this.dtoInstance = builder.build();
    }

    public Collection<Address> getMoveHistory() {
        Collection<Address> addresses = new ArrayList<>();
        for (AddressDTO addressDTO : dtoInstance.getMoveHistoryList()) {
            addresses.add(Address.fromDTO(addressDTO));
        }
        return addresses;
    }

    public void setMoveHistory(Collection<Address> moveHistory) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder()
                .clearMoveHistory();
        if (moveHistory != null) {
            for (Address address : moveHistory) {
                if (address != null) {
                    builder.addMoveHistory(address.toDTO());
                }
            }
        }
        this.dtoInstance = builder.build();
    }
}
