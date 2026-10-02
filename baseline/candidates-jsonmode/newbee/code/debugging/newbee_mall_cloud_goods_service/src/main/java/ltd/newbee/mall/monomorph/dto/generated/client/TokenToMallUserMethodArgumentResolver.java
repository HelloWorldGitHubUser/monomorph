package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.tokentomallusermethodargumentresolver.TokenToMallUserMethodArgumentResolverDTO;
import ltd.newbee.mall.monomorph.id.generated.client.NewBeeMallUserTokenMapper;
import ltd.newbee.mall.monomorph.id.generated.helpers.IDMapper;

/**
 * Auto-generated DTO gRPC client for {@link TokenToMallUserMethodArgumentResolver}.
 */
public class TokenToMallUserMethodArgumentResolver {

    private TokenToMallUserMethodArgumentResolverDTO dtoInstance;

    private TokenToMallUserMethodArgumentResolver(TokenToMallUserMethodArgumentResolverDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public TokenToMallUserMethodArgumentResolver() {
        this(TokenToMallUserMethodArgumentResolverDTO.getDefaultInstance());
    }

    public TokenToMallUserMethodArgumentResolverDTO toDTO() {
        return this.dtoInstance;
    }

    public static TokenToMallUserMethodArgumentResolver fromDTO(
            TokenToMallUserMethodArgumentResolverDTO dtoInstance) {
        return new TokenToMallUserMethodArgumentResolver(dtoInstance);
    }

    public MallUserMapper getMallUserMapper() {
        return MallUserMapper.fromDTO(dtoInstance.getMallUserMapper());
    }

    public void setMallUserMapper(MallUserMapper mallUserMapper) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setMallUserMapper(mallUserMapper.toDTO())
                .build();
    }

    public NewBeeMallUserTokenMapper getNewBeeMallUserTokenMapper() {
        return (NewBeeMallUserTokenMapper) IDMapper.fromID(
                dtoInstance.getNewBeeMallUserTokenMapper());
    }

    public void setNewBeeMallUserTokenMapper(NewBeeMallUserTokenMapper newBeeMallUserTokenMapper) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setNewBeeMallUserTokenMapper(IDMapper.toID(newBeeMallUserTokenMapper))
                .build();
    }
}
