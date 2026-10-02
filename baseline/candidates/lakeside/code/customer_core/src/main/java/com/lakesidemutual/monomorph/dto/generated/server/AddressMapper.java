

package com.lakesidemutual.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.lakesidemutual.monomorph.dto.generated.proto.address.AddressDTO;
import com.lakesidemutual.domain.customer.Address;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link Address} and {@link AddressDTO}.
 */
@Mapper(componentModel = "default") 
public interface AddressMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    /**
     * Maps from {@link AddressDTO} to {@link Address}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link Address} object.
     */
    Address fromDTO(AddressDTO dto);

    /**
     * Maps from {@link Address} to {@link AddressDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link AddressDTO} object.
     */
    AddressDTO toDTO(Address domain);

}