package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

// gRPC imports
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallgoodsmapper.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

// DTO proxy imports
import ltd.newbee.mall.monomorph.dto.generated.client.NewBeeMallGoods;
import ltd.newbee.mall.monomorph.dto.generated.client.StockNumDTO;

/**
 * Client-side proxy for the NewBeeMallGoodsMapper service.
 * Implements only the methods exposed in the generated proto service definition.
 */
public class NewBeeMallGoodsMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_goods_service";

    private ManagedChannel businessChannel;
    private NewBeeMallGoodsMapperServiceGrpc.NewBeeMallGoodsMapperServiceBlockingStub businessStub;

    /** Default constructor for a new proxy instance. */
    public NewBeeMallGoodsMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeMallGoodsMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = NewBeeMallGoodsMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.getDefaultInstance())
                .build();
        RefactoredObjectID createResponseProto = this.businessStub.createObject(createRequest);
        return createResponseProto;
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
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Factory method for creating a proxy from an EXISTING ID. */
    public static NewBeeMallGoodsMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallGoodsMapper(existingId);
    }

    // --- Service Methods ---

    public int recoverStockNum(List<StockNumDTO> stockNumDTOS) {
        ensureRpcSetup();
        try {
            RecoverStockNumRequest.Builder requestBuilder = RecoverStockNumRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId);
            for (StockNumDTO dto : stockNumDTOS) {
                requestBuilder.addStockNumDtos(dto.toDTO());
            }
            RecoverStockNumResponse response = this.businessStub.recoverStockNum(requestBuilder.build());
            return response.getResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to call recoverStockNum", e);
        }
    }

    public NewBeeMallGoods selectByPrimaryKey(Long goodsId) {
        ensureRpcSetup();
        try {
            SelectByPrimaryKeyRequest request = SelectByPrimaryKeyRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setGoodsId(goodsId)
                    .build();
            SelectByPrimaryKeyResponse response = this.businessStub.selectByPrimaryKey(request);
            return NewBeeMallGoods.fromDTO(response.getGoods());
        } catch (Exception e) {
            throw new RuntimeException("Failed to call selectByPrimaryKey", e);
        }
    }

    public List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds) {
        ensureRpcSetup();
        try {
            SelectByPrimaryKeysRequest.Builder requestBuilder = SelectByPrimaryKeysRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId);
            for (Long id : goodsIds) {
                requestBuilder.addGoodsIds(id);
            }
            SelectByPrimaryKeysResponse response = this.businessStub.selectByPrimaryKeys(requestBuilder.build());
            List<NewBeeMallGoods> result = new ArrayList<>();
            for (NewBeeMallGoodsDTO dto : response.getGoodsListList()) {
                result.add(NewBeeMallGoods.fromDTO(dto));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call selectByPrimaryKeys", e);
        }
    }

    public int updateStockNum(List<StockNumDTO> stockNumDTOS) {
        ensureRpcSetup();
        try {
            UpdateStockNumRequest.Builder requestBuilder = UpdateStockNumRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId);
            for (StockNumDTO dto : stockNumDTOS) {
                requestBuilder.addStockNumDtos(dto.toDTO());
            }
            UpdateStockNumResponse response = this.businessStub.updateStockNum(requestBuilder.build());
            return response.getResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to call updateStockNum", e);
        }
    }

    // --- Private helper to ensure RPC setup is performed lazily ---
    private void ensureRpcSetup() {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC channel", e);
            }
        }
    }
}