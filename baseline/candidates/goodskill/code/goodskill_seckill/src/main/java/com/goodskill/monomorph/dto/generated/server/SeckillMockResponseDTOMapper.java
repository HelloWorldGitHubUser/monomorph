

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.seckillmockresponsedto.SeckillMockResponseDTODTO;
import com.goodskill.dto.SeckillMockResponseDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SeckillMockResponseDTO} and {@link SeckillMockResponseDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface SeckillMockResponseDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SeckillMockResponseDTOMapper INSTANCE = Mappers.getMapper(SeckillMockResponseDTOMapper.class);

    /**
     * Maps from {@link SeckillMockResponseDTODTO} to {@link SeckillMockResponseDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SeckillMockResponseDTO} object.
     */
    SeckillMockResponseDTO fromDTO(SeckillMockResponseDTODTO dto);

    /**
     * Maps from {@link SeckillMockResponseDTO} to {@link SeckillMockResponseDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SeckillMockResponseDTODTO} object.
     */
    SeckillMockResponseDTODTO toDTO(SeckillMockResponseDTO domain);

}