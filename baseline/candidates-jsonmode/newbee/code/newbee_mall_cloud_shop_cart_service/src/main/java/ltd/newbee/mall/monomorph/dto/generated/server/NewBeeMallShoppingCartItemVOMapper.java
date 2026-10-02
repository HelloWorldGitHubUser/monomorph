

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallshoppingcartitemvo.NewBeeMallShoppingCartItemVODTO;
import ltd.newbee.mall.api.mall.vo.NewBeeMallShoppingCartItemVO;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link NewBeeMallShoppingCartItemVO} and {@link NewBeeMallShoppingCartItemVODTO}.
 */
@Mapper(componentModel = "default") 
public interface NewBeeMallShoppingCartItemVOMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    NewBeeMallShoppingCartItemVOMapper INSTANCE = Mappers.getMapper(NewBeeMallShoppingCartItemVOMapper.class);

    /**
     * Maps from {@link NewBeeMallShoppingCartItemVODTO} to {@link NewBeeMallShoppingCartItemVO}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link NewBeeMallShoppingCartItemVO} object.
     */
    NewBeeMallShoppingCartItemVO fromDTO(NewBeeMallShoppingCartItemVODTO dto);

    /**
     * Maps from {@link NewBeeMallShoppingCartItemVO} to {@link NewBeeMallShoppingCartItemVODTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link NewBeeMallShoppingCartItemVODTO} object.
     */
    NewBeeMallShoppingCartItemVODTO toDTO(NewBeeMallShoppingCartItemVO domain);

}