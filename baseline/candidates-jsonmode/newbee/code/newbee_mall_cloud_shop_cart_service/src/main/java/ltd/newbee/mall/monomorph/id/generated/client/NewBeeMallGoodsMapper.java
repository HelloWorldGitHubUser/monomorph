package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallgoodsmapper.*;
import ltd.newbee.mall.monomorph.dto.generated.client.NewBeeMallGoods;
import ltd.newbee.mall.monomorph.dto.generated.client.StockNumDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewBeeMallGoodsMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_goods_service";

    private ManagedChannel businessChannel;
    private NewBeeMallGoodsMapperServiceGrpc.NewBeeMallGoodsMapperServiceBlockingStub businessStub;

    public NewBeeMallGoodsMapper() {
        initialize();
    }

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
                .setClientId(clientId)
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
                Thread.currentThread().interrupt();
            }
        }
    }

    public static NewBeeMallGoodsMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallGoodsMapper(existingId);
    }

    // --- Service methods ---

    public int recoverStockNum(List<StockNumDTO> stockNumDTOS) {
        ensureRpcSetup();
        RecoverStockNumRequest.Builder requestBuilder = RecoverStockNumRequest.newBuilder()
                .setRefactoredObjectId(this.objectId);
        if (stockNumDTOS != null) {
            for (StockNumDTO stockNumDTO : stockNumDTOS) {
                requestBuilder.addStockNumDtos(stockNumDTO.toDTO());
            }
        }
        RecoverStockNumResponse response = businessStub.recoverStockNum(requestBuilder.build());
        return response.getResult();
    }

    public NewBeeMallGoods selectByPrimaryKey(Long goodsId) {
        ensureRpcSetup();
        SelectByPrimaryKeyRequest request = SelectByPrimaryKeyRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setGoodsId(goodsId)
                .build();
        SelectByPrimaryKeyResponse response = businessStub.selectByPrimaryKey(request);
        if (response.hasGoods()) {
            return NewBeeMallGoods.fromDTO(response.getGoods());
        }
        return null;
    }

    public List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds) {
        ensureRpcSetup();
        SelectByPrimaryKeysRequest.Builder requestBuilder = SelectByPrimaryKeysRequest.newBuilder()
                .setRefactoredObjectId(this.objectId);
        if (goodsIds != null) {
            requestBuilder.addAllGoodsIds(goodsIds);
        }
        SelectByPrimaryKeysResponse response = businessStub.selectByPrimaryKeys(requestBuilder.build());
        List<NewBeeMallGoods> result = new ArrayList<>();
        for (NewBeeMallGoodsDTO dto : response.getGoodsListList()) {
            result.add(NewBeeMallGoods.fromDTO(dto));
        }
        return result;
    }

    public int updateStockNum(List<StockNumDTO> stockNumDTOS) {
        ensureRpcSetup();
        UpdateStockNumRequest.Builder requestBuilder = UpdateStockNumRequest.newBuilder()
                .setRefactoredObjectId(this.objectId);
        if (stockNumDTOS != null) {
            for (StockNumDTO stockNumDTO : stockNumDTOS) {
                requestBuilder.addStockNumDtos(stockNumDTO.toDTO());
            }
        }
        UpdateStockNumResponse response = businessStub.updateStockNum(requestBuilder.build());
        return response.getResult();
    }

    private void ensureRpcSetup() {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to setup gRPC channel for " + TARGET_SERVICE_ID, e);
            }
        }
    }
}