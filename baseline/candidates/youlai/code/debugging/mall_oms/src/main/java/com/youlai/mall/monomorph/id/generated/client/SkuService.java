package com.youlai.mall.monomorph.id.generated.client;

import com.youlai.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;
import com.youlai.mall.monomorph.id.generated.proto.skuservice.*;
import com.youlai.mall.monomorph.dto.generated.client.SkuInfoDTO;
import com.youlai.mall.monomorph.dto.generated.client.LockSkuDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SkuService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "mall_pms";
    private static final long CHANNEL_SHUTDOWN_TIMEOUT_SECONDS = 5L;
    private static final Object RPC_SETUP_LOCK = new Object();

    private volatile ManagedChannel businessChannel;
    private volatile SkuServiceServiceGrpc.SkuServiceServiceBlockingStub businessStub;

    public SkuService() {
        initialize();
    }

    private SkuService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        synchronized (RPC_SETUP_LOCK) {
            if (businessStub != null && businessChannel != null && !businessChannel.isShutdown()) {
                return;
            }

            ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
            if (endpoint == null) {
                throw new IllegalStateException("No service endpoint registered for " + TARGET_SERVICE_ID);
            }

            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(endpoint.getHost(), endpoint.getPort())
                    .usePlaintext()
                    .build();
            this.businessChannel = channel;
            this.businessStub = SkuServiceServiceGrpc.newBlockingStub(channel);
        }
    }

    private void ensureRpcSetup() {
        if (businessStub == null || businessChannel == null || businessChannel.isShutdown()) {
            synchronized (RPC_SETUP_LOCK) {
                if (businessStub == null || businessChannel == null || businessChannel.isShutdown()) {
                    try {
                        performRpcSetup();
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to setup gRPC channel", e);
                    }
                }
            }
        }
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        ensureRpcSetup();
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientId(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
                .build();
        return this.businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        ManagedChannel channelToClose;
        synchronized (RPC_SETUP_LOCK) {
            channelToClose = this.businessChannel;
            this.businessChannel = null;
            this.businessStub = null;
        }

        if (channelToClose == null || channelToClose.isShutdown()) {
            return;
        }

        try {
            channelToClose.shutdown().awaitTermination(CHANNEL_SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!channelToClose.isTerminated()) {
                channelToClose.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            channelToClose.shutdownNow();
        }
    }

    public static SkuService fromID(RefactoredObjectID existingId) {
        return new SkuService(existingId);
    }

    public SkuInfoDTO getSkuInfo(Long skuId) {
        ensureRpcSetup();
        GetSkuInfoRequest request = GetSkuInfoRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setSkuId(skuId)
                .build();
        GetSkuInfoResponse response = this.businessStub.getSkuInfo(request);
        if (response.hasSkuInfo()) {
            return SkuInfoDTO.fromDTO(response.getSkuInfo());
        }
        return null;
    }

    public List<SkuInfoDTO> getSkuInfoList(List<Long> skuIds) {
        ensureRpcSetup();
        GetSkuInfoListRequest request = GetSkuInfoListRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .addAllSkuIds(skuIds)
                .build();
        GetSkuInfoListResponse response = this.businessStub.getSkuInfoList(request);
        return response.getSkuInfosList().stream()
                .map(SkuInfoDTO::fromDTO)
                .collect(Collectors.toList());
    }

    public boolean lockStock(String orderToken, List<LockSkuDTO> lockSkuList) {
        ensureRpcSetup();
        LockStockRequest request = LockStockRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setOrderToken(orderToken)
                .addAllLockSkuList(lockSkuList.stream()
                        .map(LockSkuDTO::toDTO)
                        .collect(Collectors.toList()))
                .build();
        LockStockResponse response = this.businessStub.lockStock(request);
        return response.getSuccess();
    }

    public boolean unlockStock(String orderSn) {
        ensureRpcSetup();
        UnlockStockRequest request = UnlockStockRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setOrderSn(orderSn)
                .build();
        UnlockStockResponse response = this.businessStub.unlockStock(request);
        return response.getSuccess();
    }

    public boolean deductStock(String orderSn) {
        ensureRpcSetup();
        DeductStockRequest request = DeductStockRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setOrderSn(orderSn)
                .build();
        DeductStockResponse response = this.businessStub.deductStock(request);
        return response.getSuccess();
    }
}
