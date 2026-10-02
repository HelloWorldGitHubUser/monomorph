package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallshoppingcartitemmapper.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class NewBeeMallShoppingCartItemMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_shop_cart_service";

    private ManagedChannel businessChannel;
    private NewBeeMallShoppingCartItemMapperServiceGrpc.NewBeeMallShoppingCartItemMapperServiceBlockingStub businessStub;

    /** Public constructor for creating a new remote object. */
    public NewBeeMallShoppingCartItemMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeMallShoppingCartItemMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        if (this.businessStub != null) {
            return; // already initialised
        }
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = NewBeeMallShoppingCartItemMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.getDefaultInstance())
                .build();

        return this.businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
            try {
                this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!this.businessChannel.isTerminated()) {
                    this.businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    /** Factory method for creating proxy from an EXISTING ID. */
    public static NewBeeMallShoppingCartItemMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallShoppingCartItemMapper(existingId);
    }

    // --- Implementation of the remaining service methods ---

    /**
     * Deletes a batch of shopping cart items by their IDs.
     * Corresponds to the original {@code int deleteBatch(java.util.List<Long> ids)} method.
     */
    public int deleteBatch(java.util.List<java.lang.Long> ids) {
        ensureChannel();

        DeleteBatchRequest request = DeleteBatchRequest.newBuilder()
                .setObjectId(this.objectId)
                .addAllIds(ids)
                .build();

        DeleteBatchResponse response = this.businessStub.deleteBatch(request);
        return response.getDeletedCount();
    }

    // --- Private helper methods ---

    private void ensureChannel() {
        if (this.businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC channel", e);
            }
        }
    }
}