

package com.passjava.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.passjava.monomorph.dto.generated.proto.searchservice.SearchServiceDTO;
import com.passjava.service.SearchService;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SearchService} and {@link SearchServiceDTO}.
 */
@Mapper(componentModel = "default") 
public interface SearchServiceMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SearchServiceMapper INSTANCE = Mappers.getMapper(SearchServiceMapper.class);

    /**
     * Maps from {@link SearchServiceDTO} to {@link SearchService}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SearchService} object.
     */
    SearchService fromDTO(SearchServiceDTO dto);

    /**
     * Maps from {@link SearchService} to {@link SearchServiceDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SearchServiceDTO} object.
     */
    SearchServiceDTO toDTO(SearchService domain);

}