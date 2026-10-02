package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallgoodsmapper.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;

import ltd.newbee.mall.monomorph.dto.generated.client.NewBeeMallGoods;
import ltd.newbee.mall.monomorph.dto.generated.client.StockNumDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.stocknumdto.StockNumDTODTO;

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
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = NewBeeMallGoodsMapperServiceGrpc.newBlockingStub(businessChannel);
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
                // Ignore
            }
        }
    }

    public static NewBeeMallGoodsMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallGoodsMapper(existingId);
    }

    public int recoverStockNum(List<StockNumDTO> stockNumDTOS) {
        RecoverStockNumRequest.Builder requestBuilder = RecoverStockNumRequest.newBuilder()
                .setRefactoredObjectId(this.objectId);
        for (StockNumDTO stockNumDTO : stockNumDTOS) {
            requestBuilder.addStockNumDtos(stockNumDTO.toDTO());
        }
        RecoverStockNumRequest request = requestBuilder.build();
        RecoverStockNumResponse response = this.businessStub.recoverStockNum(request);
        return response.getResult();
    }

    public NewBeeMallGoods selectByPrimaryKey(Long goodsId) {
        SelectByPrimaryKeyRequest request = SelectByPrimaryKeyRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setGoodsId(goodsId)
                .build();
        SelectByPrimaryKeyResponse response = this.businessStub.selectByPrimaryKey(request);
        return NewBeeMallGoods.fromDTO(response.getGoods());
    }

    public List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds) {
        SelectByPrimaryKeysRequest.Builder requestBuilder = SelectByPrimaryKeysRequest.newBuilder()
                .setRefactoredObjectId(this.objectId);
        for (Long goodsId : goodsIds) {
            requestBuilder.addGoodsIds(goodsId);
        }
        SelectByPrimaryKeysResponse response = this.businessStub.selectByPrimaryKeys(requestBuilder.build());
        List<NewBeeMallGoods> result = new ArrayList<>();
        for (NewBeeMallGoodsDTO dto : response.getGoodsListList()) {
            result.add(NewBeeMallGoods.fromDTO(dto));
        }
        return result;
    }

    public int updateStockNum(List<StockNumDTO> stockNumDTOS) {
        UpdateStockNumRequest.Builder requestBuilder = UpdateStockNumRequest.newBuilder()
                .setRefactoredObjectId(this.objectId);
        for (StockNumDTO stockNumDTO : stockNumDTOS) {
            requestBuilder.addStockNumDtos(stockNumDTO.toDTO());
        }
        UpdateStockNumRequest request = requestBuilder.build();
        UpdateStockNumResponse response = this.businessStub.updateStockNum(request);
        return response.getResult();
    }
}