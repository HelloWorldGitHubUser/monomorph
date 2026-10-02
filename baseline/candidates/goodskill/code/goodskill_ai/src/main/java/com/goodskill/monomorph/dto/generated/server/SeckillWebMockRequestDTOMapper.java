

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.seckillwebmockrequestdto.SeckillWebMockRequestDTODTO;
import com.goodskill.dto.SeckillWebMockRequestDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SeckillWebMockRequestDTO} and {@link SeckillWebMockRequestDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface SeckillWebMockRequestDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SeckillWebMockRequestDTOMapper INSTANCE = Mappers.getMapper(SeckillWebMockRequestDTOMapper.class);

    /**
     * Maps from {@link SeckillWebMockRequestDTODTO} to {@link SeckillWebMockRequestDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SeckillWebMockRequestDTO} object.
     */
    SeckillWebMockRequestDTO fromDTO(SeckillWebMockRequestDTODTO dto);

    /**
     * Maps from {@link SeckillWebMockRequestDTO} to {@link SeckillWebMockRequestDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SeckillWebMockRequestDTODTO} object.
     */
    SeckillWebMockRequestDTODTO toDTO(SeckillWebMockRequestDTO domain);

}