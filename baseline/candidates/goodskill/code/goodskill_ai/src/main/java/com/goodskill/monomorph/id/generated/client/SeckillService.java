package com.goodskill.monomorph.id.generated.client;

import com.goodskill.monomorph.id.shared.client.AbstractRefactoredClient;
import com.goodskill.monomorph.id.generated.helpers.ServiceRegistry;
import com.goodskill.monomorph.id.shared.RefactoredObjectID;
import com.goodskill.monomorph.id.generated.proto.seckillservice.*;
import com.goodskill.monomorph.dto.generated.client.SeckillMockRequestDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class SeckillService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "goodskill_seckill";

    private ManagedChannel businessChannel;
    private SeckillServiceServiceGrpc.SeckillServiceServiceBlockingStub businessStub;

    public SeckillService() {
        initialize();
    }

    private SeckillService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = SeckillServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        ensureChannel();

        ConstructorArgs constructorArgs = ConstructorArgs.newBuilder().build();
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(constructorArgs)
                .build();

        return businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        if (businessChannel != null && !businessChannel.isShutdown()) {
            try {
                businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!businessChannel.isTerminated()) {
                    businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                businessChannel.shutdownNow();
            }
        }
        businessChannel = null;
        businessStub = null;
    }

    private void ensureChannel() {
        if (businessStub != null && businessChannel != null && !businessChannel.isShutdown()) {
            return;
        }
        try {
            performRpcSetup();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialize gRPC channel for " + TARGET_SERVICE_ID, e);
        }
    }

    public static SeckillService fromID(RefactoredObjectID existingId) {
        return new SeckillService(existingId);
    }

    public boolean endSeckill(Long seckillId) {
        ensureChannel();
        EndSeckillRequest request = EndSeckillRequest.newBuilder()
                .setObjectId(objectId)
                .setSeckillId(seckillId)
                .build();
        EndSeckillResponse response = businessStub.endSeckill(request);
        return response.getSuccess();
    }

    public void execute(SeckillMockRequestDTO requestDto, int strategyNumber) {
        ensureChannel();
        ExecuteRequest request = ExecuteRequest.newBuilder()
                .setObjectId(objectId)
                .setRequestDto(requestDto.toDTO())
                .setStrategyNumber(strategyNumber)
                .build();
        businessStub.execute(request);
    }

    public long getSuccessKillCount(Long seckillId) {
        ensureChannel();
        GetSuccessKillCountRequest request = GetSuccessKillCountRequest.newBuilder()
                .setObjectId(objectId)
                .setSeckillId(seckillId)
                .build();
        GetSuccessKillCountResponse response = businessStub.getSuccessKillCount(request);
        return response.getCount();
    }

    public void prepareSeckill(Long seckillId, int seckillCount, String taskId) {
        ensureChannel();
        PrepareSeckillRequest request = PrepareSeckillRequest.newBuilder()
                .setObjectId(objectId)
                .setSeckillId(seckillId)
                .setSeckillCount(seckillCount)
                .setTaskId(taskId)
                .build();
        businessStub.prepareSeckill(request);
    }
}