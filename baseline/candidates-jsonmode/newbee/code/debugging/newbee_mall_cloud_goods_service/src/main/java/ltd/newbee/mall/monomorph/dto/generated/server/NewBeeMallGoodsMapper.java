

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;
import ltd.newbee.mall.entity.NewBeeMallGoods;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link NewBeeMallGoods} and {@link NewBeeMallGoodsDTO}.
 */
@Mapper(componentModel = "default") 
public interface NewBeeMallGoodsMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    NewBeeMallGoodsMapper INSTANCE = Mappers.getMapper(NewBeeMallGoodsMapper.class);

    /**
     * Maps from {@link NewBeeMallGoodsDTO} to {@link NewBeeMallGoods}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link NewBeeMallGoods} object.
     */
    NewBeeMallGoods fromDTO(NewBeeMallGoodsDTO dto);

    /**
     * Maps from {@link NewBeeMallGoods} to {@link NewBeeMallGoodsDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link NewBeeMallGoodsDTO} object.
     */
    NewBeeMallGoodsDTO toDTO(NewBeeMallGoods domain);

}