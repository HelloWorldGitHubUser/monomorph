

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;
import ltd.newbee.mall.entity.AdminUserToken;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link AdminUserToken} and {@link AdminUserTokenDTO}.
 */
@Mapper(componentModel = "default") 
public interface AdminUserTokenMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    AdminUserTokenMapper INSTANCE = Mappers.getMapper(AdminUserTokenMapper.class);

    /**
     * Maps from {@link AdminUserTokenDTO} to {@link AdminUserToken}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link AdminUserToken} object.
     */
    AdminUserToken fromDTO(AdminUserTokenDTO dto);

    /**
     * Maps from {@link AdminUserToken} to {@link AdminUserTokenDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link AdminUserTokenDTO} object.
     */
    AdminUserTokenDTO toDTO(AdminUserToken domain);

}