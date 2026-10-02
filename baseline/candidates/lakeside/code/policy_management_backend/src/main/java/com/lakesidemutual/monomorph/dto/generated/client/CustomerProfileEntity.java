package com.lakesidemutual.monomorph.dto.generated.client;

import com.google.protobuf.Timestamp;
import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.CustomerProfileEntityDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated DTO gRPC client for {@link CustomerProfileEntity} and {@link CustomerProfileEntityDTO}.
 * Uses composition to maintain the original API while delegating data storage to the protobuf DTO.
 */
public class CustomerProfileEntity {
    private CustomerProfileEntityDTO dtoInstance;

    private CustomerProfileEntity(CustomerProfileEntityDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public CustomerProfileEntity() {
        this.dtoInstance = CustomerProfileEntityDTO.getDefaultInstance();
    }

    public CustomerProfileEntity(String firstname, String lastname, Date birthday, Address currentAddress, String email, String phoneNumber) {
        CustomerProfileEntityDTO.Builder builder = CustomerProfileEntityDTO.newBuilder();
        if (firstname != null) builder.setFirstname(firstname);
        if (lastname != null) builder.setLastname(lastname);
        if (birthday != null) builder.setBirthday(toTimestamp(birthday));
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

    public long getCustomerId() {
        return dtoInstance.getCustomerId();
    }

    public void setCustomerId(long customerId) {
        this.dtoInstance = this.dtoInstance.toBuilder().setCustomerId(customerId).build();
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
        if (!dtoInstance.hasBirthday()) {
            return null;
        }
        Timestamp ts = dtoInstance.getBirthday();
        return new Date(ts.getSeconds() * 1000 + ts.getNanos() / 1_000_000);
    }

    public void setBirthday(Date birthday) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder();
        if (birthday == null) {
            builder.clearBirthday();
        } else {
            builder.setBirthday(toTimestamp(birthday));
        }
        this.dtoInstance = builder.build();
    }

    public Address getCurrentAddress() {
        if (!dtoInstance.hasCurrentAddress()) {
            return null;
        }
        return Address.fromDTO(dtoInstance.getCurrentAddress());
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
        List<Address> result = new ArrayList<>();
        for (AddressDTO addressDTO : dtoInstance.getMoveHistoryList()) {
            result.add(Address.fromDTO(addressDTO));
        }
        return result;
    }

    public void setMoveHistory(Collection<Address> moveHistory) {
        CustomerProfileEntityDTO.Builder builder = this.dtoInstance.toBuilder().clearMoveHistory();
        if (moveHistory != null) {
            for (Address address : moveHistory) {
                builder.addMoveHistory(address.toDTO());
            }
        }
        this.dtoInstance = builder.build();
    }

    private static Timestamp toTimestamp(Date date) {
        long millis = date.getTime();
        long seconds = millis / 1000;
        int nanos = (int) (millis % 1000) * 1_000_000;
        if (nanos < 0) {
            nanos += 1_000_000_000;
            seconds--;
        }
        return Timestamp.newBuilder().setSeconds(seconds).setNanos(nanos).build();
    }
}