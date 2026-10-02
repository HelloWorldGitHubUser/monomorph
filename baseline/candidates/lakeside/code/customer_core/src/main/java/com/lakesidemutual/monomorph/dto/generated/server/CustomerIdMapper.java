

package com.lakesidemutual.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.lakesidemutual.monomorph.dto.generated.proto.customerid.CustomerIdDTO;
import com.lakesidemutual.domain.customer.CustomerId;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link CustomerId} and {@link CustomerIdDTO}.
 */
@Mapper(componentModel = "default") 
public interface CustomerIdMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    CustomerIdMapper INSTANCE = Mappers.getMapper(CustomerIdMapper.class);

    /**
     * Maps from {@link CustomerIdDTO} to {@link CustomerId}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link CustomerId} object.
     */
    CustomerId fromDTO(CustomerIdDTO dto);

    /**
     * Maps from {@link CustomerId} to {@link CustomerIdDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link CustomerIdDTO} object.
     */
    CustomerIdDTO toDTO(CustomerId domain);

}