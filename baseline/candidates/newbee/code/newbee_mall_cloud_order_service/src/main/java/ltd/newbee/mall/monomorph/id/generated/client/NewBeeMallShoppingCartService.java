package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

// gRPC imports
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallshoppingcartservice.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// DTO imports
import ltd.newbee.mall.monomorph.dto.generated.client.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallshoppingcartitemvo.NewBeeMallShoppingCartItemVODTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewBeeMallShoppingCartService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_shop_cart_service";

    private ManagedChannel businessChannel;
    private NewBeeMallShoppingCartServiceServiceGrpc.NewBeeMallShoppingCartServiceServiceBlockingStub businessStub;

    /** Public no-argument constructor for creating a new remote object. */
    public NewBeeMallShoppingCartService() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeMallShoppingCartService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = NewBeeMallShoppingCartServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
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
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Factory method for creating proxy from an EXISTING ID. */
    public static NewBeeMallShoppingCartService fromID(RefactoredObjectID existingId) {
        return new NewBeeMallShoppingCartService(existingId);
    }

    // --- Business method implementation ---

    /**
     * Returns the shopping cart items for settlement.
     *
     * @param cartItemIds        list of cart item IDs
     * @param newBeeMallUserId   user ID
     * @return list of proxy DTOs representing the items
     */
    public List<NewBeeMallShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long newBeeMallUserId) {
        if (this.businessStub == null) {
            throw new IllegalStateException("Client not initialized. Ensure the object was created via constructor or fromID().");
        }

        GetCartItemsForSettleRequest.Builder requestBuilder = GetCartItemsForSettleRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setNewBeeMallUserId(newBeeMallUserId);

        if (cartItemIds != null) {
            requestBuilder.addAllCartItemIds(cartItemIds);
        }

        GetCartItemsForSettleResponse response = this.businessStub.getCartItemsForSettle(requestBuilder.build());

        List<NewBeeMallShoppingCartItemVO> result = new ArrayList<>();
        for (NewBeeMallShoppingCartItemVODTO dto : response.getItemsList()) {
            result.add(NewBeeMallShoppingCartItemVO.fromDTO(dto));
        }
        return result;
    }
}