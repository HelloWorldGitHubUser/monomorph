package ltd.newbee.mall.monomorph.dto.generated.server;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;
import ltd.newbee.mall.entity.NewBeeMallGoods;

import java.util.Date;

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
     * @param domain The source of the Original object.
     * @return The mapped {@link NewBeeMallGoodsDTO} object.
     */
    NewBeeMallGoodsDTO toDTO(NewBeeMallGoods domain);

    /**
     * Maps a protobuf {@link Timestamp} to a {@link Date}.
     *
     * @param value The source timestamp.
     * @return The mapped date.
     */
    default Date map(Timestamp value) {
        if (value == null) {
            return null;
        }
        return new Date(value.getSeconds() * 1000L + value.getNanos() / 1000000L);
    }

    /**
     * Maps a {@link Date} to a protobuf {@link Timestamp}.
     *
     * @param value The source date.
     * @return The mapped timestamp.
     */
    default Timestamp map(Date value) {
        if (value == null) {
            return null;
        }
        long millis = value.getTime();
        return Timestamp.newBuilder()
                .setSeconds(millis / 1000L)
                .setNanos((int) ((millis % 1000L) * 1000000L))
                .build();
    }
}

