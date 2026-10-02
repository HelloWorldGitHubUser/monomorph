package ltd.newbee.mall.monomorph.dto.generated.server;

import java.util.Date;

import com.google.protobuf.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenDTO;
import ltd.newbee.mall.entity.MallUserToken;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link MallUserToken} and {@link MallUserTokenDTO}.
 */
@Mapper(componentModel = "default")
public interface MallUserTokenMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    MallUserTokenMapper INSTANCE = Mappers.getMapper(MallUserTokenMapper.class);

    /**
     * Maps from {@link MallUserTokenDTO} to {@link MallUserToken}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link MallUserToken} object.
     */
    MallUserToken fromDTO(MallUserTokenDTO dto);

    /**
     * Maps from {@link MallUserToken} to {@link MallUserTokenDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link MallUserTokenDTO} object.
     */
    MallUserTokenDTO toDTO(MallUserToken domain);

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

