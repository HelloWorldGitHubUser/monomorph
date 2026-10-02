

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.result.ResultDTO;
import com.goodskill.dto.Result;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link Result} and {@link ResultDTO}.
 */
@Mapper(componentModel = "default") 
public interface ResultMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

    /**
     * Maps from {@link ResultDTO} to {@link Result}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link Result} object.
     */
    Result fromDTO(ResultDTO dto);

    /**
     * Maps from {@link Result} to {@link ResultDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link ResultDTO} object.
     */
    ResultDTO toDTO(Result domain);

}