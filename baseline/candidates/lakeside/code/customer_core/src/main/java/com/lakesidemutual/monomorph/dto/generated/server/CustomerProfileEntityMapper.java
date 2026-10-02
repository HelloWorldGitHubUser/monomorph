

package com.lakesidemutual.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.CustomerProfileEntityDTO;
import com.lakesidemutual.domain.customer.CustomerProfileEntity;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link CustomerProfileEntity} and {@link CustomerProfileEntityDTO}.
 */
@Mapper(componentModel = "default") 
public interface CustomerProfileEntityMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    CustomerProfileEntityMapper INSTANCE = Mappers.getMapper(CustomerProfileEntityMapper.class);

    /**
     * Maps from {@link CustomerProfileEntityDTO} to {@link CustomerProfileEntity}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link CustomerProfileEntity} object.
     */
    CustomerProfileEntity fromDTO(CustomerProfileEntityDTO dto);

    /**
     * Maps from {@link CustomerProfileEntity} to {@link CustomerProfileEntityDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link CustomerProfileEntityDTO} object.
     */
    CustomerProfileEntityDTO toDTO(CustomerProfileEntity domain);

}