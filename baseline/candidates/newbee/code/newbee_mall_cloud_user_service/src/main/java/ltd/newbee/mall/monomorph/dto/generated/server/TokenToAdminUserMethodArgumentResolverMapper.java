

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.tokentoadminusermethodargumentresolver.TokenToAdminUserMethodArgumentResolverDTO;
import ltd.newbee.mall.config.handler.TokenToAdminUserMethodArgumentResolver;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link TokenToAdminUserMethodArgumentResolver} and {@link TokenToAdminUserMethodArgumentResolverDTO}.
 */
@Mapper(componentModel = "default") 
public interface TokenToAdminUserMethodArgumentResolverMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    TokenToAdminUserMethodArgumentResolverMapper INSTANCE = Mappers.getMapper(TokenToAdminUserMethodArgumentResolverMapper.class);

    /**
     * Maps from {@link TokenToAdminUserMethodArgumentResolverDTO} to {@link TokenToAdminUserMethodArgumentResolver}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link TokenToAdminUserMethodArgumentResolver} object.
     */
    TokenToAdminUserMethodArgumentResolver fromDTO(TokenToAdminUserMethodArgumentResolverDTO dto);

    /**
     * Maps from {@link TokenToAdminUserMethodArgumentResolver} to {@link TokenToAdminUserMethodArgumentResolverDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link TokenToAdminUserMethodArgumentResolverDTO} object.
     */
    TokenToAdminUserMethodArgumentResolverDTO toDTO(TokenToAdminUserMethodArgumentResolver domain);

}