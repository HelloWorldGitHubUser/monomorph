

package com.lakesidemutual.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.lakesidemutual.monomorph.dto.generated.proto.page.PageDTO;
import com.lakesidemutual.application.Page;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link Page} and {@link PageDTO}.
 */
@Mapper(componentModel = "default") 
public interface PageMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    PageMapper INSTANCE = Mappers.getMapper(PageMapper.class);

    /**
     * Maps from {@link PageDTO} to {@link Page}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link Page} object.
     */
    Page fromDTO(PageDTO dto);

    /**
     * Maps from {@link Page} to {@link PageDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link PageDTO} object.
     */
    PageDTO toDTO(Page domain);

}