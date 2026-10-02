

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.stocknumdto.StockNumDTODTO;
import ltd.newbee.mall.entity.StockNumDTO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link StockNumDTO} and {@link StockNumDTODTO}.
 */
@Mapper(componentModel = "default") 
public interface StockNumDTOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    StockNumDTOMapper INSTANCE = Mappers.getMapper(StockNumDTOMapper.class);

    /**
     * Maps from {@link StockNumDTODTO} to {@link StockNumDTO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link StockNumDTO} object.
     */
    StockNumDTO fromDTO(StockNumDTODTO dto);

    /**
     * Maps from {@link StockNumDTO} to {@link StockNumDTODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link StockNumDTODTO} object.
     */
    StockNumDTODTO toDTO(StockNumDTO domain);

}