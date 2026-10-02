package ltd.newbee.mall.monomorph.dto.generated.server;

import java.util.Date;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.MallUserDTO;
import ltd.newbee.mall.entity.MallUser;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link MallUser} and {@link MallUserDTO}.
 */
@Mapper(componentModel = "default")
public interface MallUserMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    MallUserMapper INSTANCE = Mappers.getMapper(MallUserMapper.class);

    /**
     * Maps from {@link MallUserDTO} to {@link MallUser}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link MallUser} object.
     */
    MallUser fromDTO(MallUserDTO dto);

    /**
     * Maps from {@link MallUser} to {@link MallUserDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link MallUserDTO} object.
     */
    MallUserDTO toDTO(MallUser domain);

    default Date map(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000);
    }

    default Timestamp map(Date date) {
        if (date == null) {
            return null;
        }
        long millis = date.getTime();
        long seconds = Math.floorDiv(millis, 1000);
        int nanos = (int) Math.floorMod(millis, 1000) * 1000000;
        return Timestamp.newBuilder().setSeconds(seconds).setNanos(nanos).build();
    }
}

