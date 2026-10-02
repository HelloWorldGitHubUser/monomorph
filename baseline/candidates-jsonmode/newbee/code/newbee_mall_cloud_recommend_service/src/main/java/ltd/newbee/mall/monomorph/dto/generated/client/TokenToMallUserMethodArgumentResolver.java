package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.tokentomallusermethodargumentresolver.*;

import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.MallUserMapperDTO;
import ltd.newbee.mall.monomorph.id.generated.client.NewBeeMallUserTokenMapper;
import ltd.newbee.mall.monomorph.id.generated.helpers.IDMapper;

/**
 * Auto-generated DTO gRPC client
 * {@link TokenToMallUserMethodArgumentResolver} and
 * {@link TokenToMallUserMethodArgumentResolverDTO}.
 */
public class TokenToMallUserMethodArgumentResolver {

    private TokenToMallUserMethodArgumentResolverDTO dtoInstance;

    public TokenToMallUserMethodArgumentResolver() {
        this.dtoInstance = TokenToMallUserMethodArgumentResolverDTO.newBuilder().build();
    }

    public TokenToMallUserMethodArgumentResolver(TokenToMallUserMethodArgumentResolverDTO dtoInstance) {
        // dtoConstructor to initialize from a DTO instance
        this.dtoInstance = dtoInstance;
    }

    // mapping methods
    public TokenToMallUserMethodArgumentResolverDTO toDTO() {
        return this.dtoInstance;
    }

    public static TokenToMallUserMethodArgumentResolver fromDTO(
            TokenToMallUserMethodArgumentResolverDTO dtoInstance) {
        return new TokenToMallUserMethodArgumentResolver(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No service methods are defined in the proto for this DTO-only class.

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---

    public MallUserMapper getMallUserMapper() {
        if (!dtoInstance.hasMallUserMapper()) {
            return null;
        }
        MallUserMapperDTO mallUserMapperDTO = dtoInstance.getMallUserMapper();
        return MallUserMapper.fromDTO(mallUserMapperDTO);
    }

    public void setMallUserMapper(MallUserMapper mallUserMapper) {
        if (mallUserMapper == null) {
            dtoInstance = dtoInstance.toBuilder().clearMallUserMapper().build();
        } else {
            dtoInstance = dtoInstance.toBuilder()
                    .setMallUserMapper(mallUserMapper.toDTO())
                    .build();
        }
    }

    public NewBeeMallUserTokenMapper getNewBeeMallUserTokenMapper() {
        if (!dtoInstance.hasNewBeeMallUserTokenMapper()) {
            return null;
        }
        return (NewBeeMallUserTokenMapper) IDMapper.fromID(
                dtoInstance.getNewBeeMallUserTokenMapper());
    }

    public void setNewBeeMallUserTokenMapper(
            NewBeeMallUserTokenMapper newBeeMallUserTokenMapper) {
        if (newBeeMallUserTokenMapper == null) {
            dtoInstance = dtoInstance.toBuilder()
                    .clearNewBeeMallUserTokenMapper()
                    .build();
        } else {
            dtoInstance = dtoInstance.toBuilder()
                    .setNewBeeMallUserTokenMapper(IDMapper.toID(newBeeMallUserTokenMapper))
                    .build();
        }
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}