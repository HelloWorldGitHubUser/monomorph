

package com.lakesidemutual.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootDTO;
import com.lakesidemutual.domain.customer.CustomerAggregateRoot;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link CustomerAggregateRoot} and {@link CustomerAggregateRootDTO}.
 */
@Mapper(componentModel = "default") 
public interface CustomerAggregateRootMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    CustomerAggregateRootMapper INSTANCE = Mappers.getMapper(CustomerAggregateRootMapper.class);

    /**
     * Maps from {@link CustomerAggregateRootDTO} to {@link CustomerAggregateRoot}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link CustomerAggregateRoot} object.
     */
    CustomerAggregateRoot fromDTO(CustomerAggregateRootDTO dto);

    /**
     * Maps from {@link CustomerAggregateRoot} to {@link CustomerAggregateRootDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link CustomerAggregateRootDTO} object.
     */
    CustomerAggregateRootDTO toDTO(CustomerAggregateRoot domain);

}