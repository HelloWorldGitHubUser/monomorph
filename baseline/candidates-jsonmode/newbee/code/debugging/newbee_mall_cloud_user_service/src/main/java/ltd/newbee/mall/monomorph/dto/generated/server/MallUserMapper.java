

package ltd.newbee.mall.monomorph.dto.generated.server;

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

}