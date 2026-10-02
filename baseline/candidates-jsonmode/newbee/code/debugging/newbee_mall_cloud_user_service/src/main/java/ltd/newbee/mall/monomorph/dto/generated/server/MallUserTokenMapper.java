

package ltd.newbee.mall.monomorph.dto.generated.server;

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

}