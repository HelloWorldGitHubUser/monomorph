

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.tokentomallusermethodargumentresolver.TokenToMallUserMethodArgumentResolverDTO;
import ltd.newbee.mall.config.handler.TokenToMallUserMethodArgumentResolver;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link TokenToMallUserMethodArgumentResolver} and {@link TokenToMallUserMethodArgumentResolverDTO}.
 */
@Mapper(componentModel = "default") 
public interface TokenToMallUserMethodArgumentResolverMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    TokenToMallUserMethodArgumentResolverMapper INSTANCE = Mappers.getMapper(TokenToMallUserMethodArgumentResolverMapper.class);

    /**
     * Maps from {@link TokenToMallUserMethodArgumentResolverDTO} to {@link TokenToMallUserMethodArgumentResolver}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link TokenToMallUserMethodArgumentResolver} object.
     */
    TokenToMallUserMethodArgumentResolver fromDTO(TokenToMallUserMethodArgumentResolverDTO dto);

    /**
     * Maps from {@link TokenToMallUserMethodArgumentResolver} to {@link TokenToMallUserMethodArgumentResolverDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link TokenToMallUserMethodArgumentResolverDTO} object.
     */
    TokenToMallUserMethodArgumentResolverDTO toDTO(TokenToMallUserMethodArgumentResolver domain);

}