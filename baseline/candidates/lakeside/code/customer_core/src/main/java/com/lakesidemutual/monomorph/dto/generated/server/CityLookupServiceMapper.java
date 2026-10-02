

package com.lakesidemutual.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.lakesidemutual.monomorph.dto.generated.proto.citylookupservice.CityLookupServiceDTO;
import com.lakesidemutual.domain.customer.CityLookupService;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link CityLookupService} and {@link CityLookupServiceDTO}.
 */
@Mapper(componentModel = "default") 
public interface CityLookupServiceMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    CityLookupServiceMapper INSTANCE = Mappers.getMapper(CityLookupServiceMapper.class);

    /**
     * Maps from {@link CityLookupServiceDTO} to {@link CityLookupService}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link CityLookupService} object.
     */
    CityLookupService fromDTO(CityLookupServiceDTO dto);

    /**
     * Maps from {@link CityLookupService} to {@link CityLookupServiceDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link CityLookupServiceDTO} object.
     */
    CityLookupServiceDTO toDTO(CityLookupService domain);

}