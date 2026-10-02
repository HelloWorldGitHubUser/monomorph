package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ltd.newbee.mall.monomorph.dto.generated.client.MallUserToken;

/**
 * Refactored gRPC client for NewBeeMallUserTokenMapper.
 * <p>
 * Key improvements:
 * <ul>
 *   <li>Lazy, thread-safe initialization of both the gRPC channel and the remote object ID.</li>
 *   <li>Single channel instance reused for the lifetime of this client.</li>
 *   <li>No redundant RPC setup calls; channel is created only once.</li>
 *   <li>Proper channel shutdown on cleanup.</li>
 *   <li>Explicit handling of "new object" vs "existing object" creation paths.</li>
 * </ul>
 */
public class NewBeeMallUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    // Lock for lazy initialization
    private final Object initLock = new Object();

    private ManagedChannel businessChannel;
    private NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    /**
     * Indicates whether this instance represents a newly created remote object
     * (true for the no-arg constructor) or an existing object from an ID (false for fromID).
     */
    private final boolean isNewObject;

    /** Public no-arg constructor for creating a new remote object lazily. */
    public NewBeeMallUserTokenMapper() {
        this.isNewObject = true;
        // No eager initialization; everything happens lazily on first method call.
    }

    /** Private constructor used by the fromID factory for existing remote objects. */
    private NewBeeMallUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
        this.isNewObject = false;
        // objectId is already set by super(existingId).
    }

    /**
     * Factory method for creating a proxy for an EXISTING remote object.
     */
    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    /**
     * Ensures that the gRPC channel/stub are initialized and, for a new object,
     * that the remote object ID has been obtained.
     * This method is thread-safe and idempotent.
     */
    private void ensureInitialized() {
        if (businessStub != null && (!isNewObject || this.objectId != null)) {
            // Already fully initialized for the current mode.
            return;
        }

        synchronized (initLock) {
            // Double-checked locking
            if (businessStub == null) {
                try {
                    performRpcSetup();
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to initialize gRPC channel", e);
                }
            }

            // For a new object, create the remote object and obtain its ID if not already done.
            if (isNewObject && this.objectId == null) {
                try {
                    String clientId = UUID.randomUUID().toString();
                    this.objectId = performRemoteCreateAndGetId(clientId);
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to create remote object", e);
                }
            }
        }
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        // Note: In production, consider using TLS instead of plaintext.
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = NewBeeMallUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        // The channel must already be set up.
        if (businessStub == null) {
            throw new IllegalStateException("gRPC stub not initialized");
        }
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
                .build();
        return this.businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        synchronized (initLock) {
            if (businessChannel != null && !businessChannel.isShutdown()) {
                try {
                    businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                    if (!businessChannel.isTerminated()) {
                        businessChannel.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                businessChannel = null;
                businessStub = null;
            }
        }
    }

    /**
     * RPC wrapper for selectByToken.
     * Maps the returned MallUserTokenDTO back to the proxy type MallUserToken.
     */
    public MallUserToken selectByToken(String token) {
        ensureInitialized();
        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setToken(token)
                .build();
        SelectByTokenResponse response = this.businessStub.selectByToken(request);
        return MallUserToken.fromDTO(response.getMallUserToken());
    }
}
