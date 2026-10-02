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

    public static SeckillService fromID(RefactoredObjectID existingId) {
        return new SeckillService(existingId);
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
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean endSeckill(Long seckillId) {
        long seckillIdVal = seckillId == null ? 0L : seckillId;
        EndSeckillRequest request = EndSeckillRequest.newBuilder()
                .setObjectId(this.objectId)
                .setSeckillId(seckillIdVal)
                .build();
        EndSeckillResponse response = businessStub.endSeckill(request);
        return response.getSuccess();
    }

    public void execute(SeckillMockRequestDTO requestDto, int strategyNumber) {
        ExecuteRequest request = ExecuteRequest.newBuilder()
                .setObjectId(this.objectId)
                .setRequestDto(requestDto.toDTO())
                .setStrategyNumber(strategyNumber)
                .build();
        businessStub.execute(request);
    }

    public long getSuccessKillCount(Long seckillId) {
        long seckillIdVal = seckillId == null ? 0L : seckillId;
        GetSuccessKillCountRequest request = GetSuccessKillCountRequest.newBuilder()
                .setObjectId(this.objectId)
                .setSeckillId(seckillIdVal)
                .build();
        GetSuccessKillCountResponse response = businessStub.getSuccessKillCount(request);
        return response.getCount();
    }

    public void prepareSeckill(Long seckillId, int seckillCount, String taskId) {
        long seckillIdVal = seckillId == null ? 0L : seckillId;
        String taskIdVal = taskId == null ? "" : taskId;
        PrepareSeckillRequest request = PrepareSeckillRequest.newBuilder()
                .setObjectId(this.objectId)
                .setSeckillId(seckillIdVal)
                .setSeckillCount(seckillCount)
                .setTaskId(taskIdVal)
                .build();
        businessStub.prepareSeckill(request);
    }
}