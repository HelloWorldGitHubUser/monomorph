package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.tokentoadminusermethodargumentresolver.TokenToAdminUserMethodArgumentResolverDTO;
import ltd.newbee.mall.monomorph.id.generated.client.NewBeeAdminUserTokenMapper;
import ltd.newbee.mall.monomorph.id.generated.helpers.IDMapper;

/**
 * Auto-generated DTO gRPC client
 * {@link TokenToAdminUserMethodArgumentResolver} and {@link TokenToAdminUserMethodArgumentResolverDTO}.
 */
public class TokenToAdminUserMethodArgumentResolver {
    private TokenToAdminUserMethodArgumentResolverDTO dtoInstance;

    public TokenToAdminUserMethodArgumentResolver() {
        this.dtoInstance = TokenToAdminUserMethodArgumentResolverDTO.getDefaultInstance();
    }

    public TokenToAdminUserMethodArgumentResolver(TokenToAdminUserMethodArgumentResolverDTO dtoInstance) {
        this.dtoInstance = dtoInstance != null ? dtoInstance : TokenToAdminUserMethodArgumentResolverDTO.getDefaultInstance();
    }

    public TokenToAdminUserMethodArgumentResolverDTO toDTO() {
        return this.dtoInstance;
    }

    public static TokenToAdminUserMethodArgumentResolver fromDTO(TokenToAdminUserMethodArgumentResolverDTO dtoInstance) {
        return new TokenToAdminUserMethodArgumentResolver(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // (No gRPC service methods to implement)

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---
    public NewBeeAdminUserTokenMapper getNewBeeAdminUserTokenMapper() {
        return (NewBeeAdminUserTokenMapper) IDMapper.fromID(this.dtoInstance.getNewBeeAdminUserTokenMapper());
    }

    public void setNewBeeAdminUserTokenMapper(NewBeeAdminUserTokenMapper newBeeAdminUserTokenMapper) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setNewBeeAdminUserTokenMapper(IDMapper.toID(newBeeAdminUserTokenMapper))
                .build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
