

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.seckillmockrequestdto.SeckillMockRequestDTODTO;
import com.goodskill.dto.SeckillMockRequestDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SeckillMockRequestDTO} and {@link SeckillMockRequestDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface SeckillMockRequestDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SeckillMockRequestDTOMapper INSTANCE = Mappers.getMapper(SeckillMockRequestDTOMapper.class);

    /**
     * Maps from {@link SeckillMockRequestDTODTO} to {@link SeckillMockRequestDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SeckillMockRequestDTO} object.
     */
    SeckillMockRequestDTO fromDTO(SeckillMockRequestDTODTO dto);

    /**
     * Maps from {@link SeckillMockRequestDTO} to {@link SeckillMockRequestDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SeckillMockRequestDTODTO} object.
     */
    SeckillMockRequestDTODTO toDTO(SeckillMockRequestDTO domain);

}