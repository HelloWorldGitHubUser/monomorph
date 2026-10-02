

package com.passjava.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import com.passjava.utils.R;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link R} and {@link RDTO}.
 */
@Mapper(componentModel = "default") 
public interface RMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    RMapper INSTANCE = Mappers.getMapper(RMapper.class);

    /**
     * Maps from {@link RDTO} to {@link R}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link R} object.
     */
    R fromDTO(RDTO dto);

    /**
     * Maps from {@link R} to {@link RDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link RDTO} object.
     */
    RDTO toDTO(R domain);

}