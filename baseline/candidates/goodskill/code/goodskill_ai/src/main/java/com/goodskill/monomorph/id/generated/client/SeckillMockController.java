package com.goodskill.monomorph.id.generated.client;

import com.goodskill.dto.Result;
import com.goodskill.dto.SeckillWebMockRequestDTO;
import com.goodskill.monomorph.dto.generated.proto.result.ResultDTO;
import com.goodskill.monomorph.dto.generated.server.ResultMapper;
import com.goodskill.monomorph.dto.generated.proto.seckillwebmockrequestdto.SeckillWebMockRequestDTODTO;
import com.goodskill.monomorph.dto.generated.server.SeckillWebMockRequestDTOMapper;
import com.goodskill.monomorph.id.generated.helpers.ServiceRegistry;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.CreateObjectRequest;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.ConstructorArgs;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.DoWithSychronizedRequest;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.DoWithSychronizedResponse;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.GetTaskTimeInfoRequest;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.GetTaskTimeInfoResponse;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.SeckillMockControllerServiceGrpc;
import com.goodskill.monomorph.id.shared.RefactoredObjectID;
import com.goodskill.monomorph.id.shared.client.AbstractRefactoredClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class SeckillMockController extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "goodskill_web";

    private ManagedChannel businessChannel;
    private SeckillMockControllerServiceGrpc.SeckillMockControllerServiceBlockingStub businessStub;

    /**
     * Public no-arg constructor matching the original class's implicit constructor.
     */
    public SeckillMockController() {
        initialize();
    }

    /**
     * Private constructor for instances created from an existing RefactoredObjectID.
     */
    private SeckillMockController(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        // Idempotent setup: avoid creating multiple channels for the same client instance.
        if (businessChannel != null && !businessChannel.isShutdown() && businessStub != null) {
            return;
        }
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder
                .forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = SeckillMockControllerServiceGrpc.newBlockingStub(businessChannel);
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

    /**
     * Factory method for creating a client proxy from an existing ID.
     */
    public static SeckillMockController fromID(RefactoredObjectID existingId) {
        return new SeckillMockController(existingId);
    }

    /**
     * RPC wrapper for the original doWithSychronized method.
     */
    public Result<Long> doWithSychronized(SeckillWebMockRequestDTO dto) {
        ensureBusinessStub();
        SeckillWebMockRequestDTODTO dtoProto = SeckillWebMockRequestDTOMapper.INSTANCE.toDTO(dto);
        DoWithSychronizedRequest request = DoWithSychronizedRequest.newBuilder()
                .setObjectId(this.objectId)
                .setDto(dtoProto)
                .build();
        DoWithSychronizedResponse response = businessStub.doWithSychronized(request);
        return mapResult(response.getResult());
    }

    /**
     * RPC wrapper for the original getTaskTimeInfo method.
     */
    public Result<String> getTaskTimeInfo(Long seckillId) {
        ensureBusinessStub();
        GetTaskTimeInfoRequest request = GetTaskTimeInfoRequest.newBuilder()
                .setObjectId(this.objectId)
                .setSeckillId(seckillId)
                .build();
        GetTaskTimeInfoResponse response = businessStub.getTaskTimeInfo(request);
        return mapResult(response.getResult());
    }

    /**
     * Ensures the gRPC channel and stub are available before performing an RPC.
     */
    private void ensureBusinessStub() {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC channel", e);
            }
        }
    }

    /**
     * Converts a protobuf ResultDTO back to the domain Result<T> object.
     */
    @SuppressWarnings("unchecked")
    private <T> Result<T> mapResult(ResultDTO resultDTO) {
        return (Result<T>) ResultMapper.INSTANCE.fromDTO(resultDTO);
    }
}

