package com.passjava.monomorph.id.generated.client;

import com.passjava.monomorph.id.shared.client.AbstractRefactoredClient;
import com.passjava.monomorph.id.generated.helpers.ServiceRegistry;
import com.passjava.monomorph.id.shared.RefactoredObjectID;
import com.passjava.monomorph.dto.generated.client.R;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import com.passjava.monomorph.id.generated.proto.studytimeservice.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class StudyTimeService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "passjava_study";

    private ManagedChannel businessChannel;
    private StudyTimeServiceServiceGrpc.StudyTimeServiceServiceBlockingStub businessStub;

    /** Public no-arg constructor. The original interface has no constructor arguments. */
    public StudyTimeService() {
        initialize();
    }

    private StudyTimeService(RefactoredObjectID existingId) {
        super(existingId);
    }

    private void ensureRpcSetup() {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to set up gRPC channel for StudyTimeService", e);
            }
        }
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = StudyTimeServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        ConstructorArgs constructorArgs = ConstructorArgs.newBuilder().build();
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(constructorArgs)
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

    public static StudyTimeService fromID(RefactoredObjectID existingId) {
        return new StudyTimeService(existingId);
    }

    /**
     * Exposes the RPC method corresponding to StudyTimeService.getMemberStudyTimeListTest(Long).
     */
    public R getMemberStudyTimeListTest(Long id) {
        ensureRpcSetup();

        GetMemberStudyTimeListTestRequest request = GetMemberStudyTimeListTestRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setId(id)
                .build();

        RDTO response = this.businessStub.getMemberStudyTimeListTest(request);
        return R.fromDTO(response);
    }
}