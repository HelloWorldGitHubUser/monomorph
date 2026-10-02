package com.lakesidemutual.monomorph.id.generated.client;

import com.lakesidemutual.monomorph.id.shared.client.AbstractRefactoredClient;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;
import com.lakesidemutual.monomorph.id.shared.RefactoredObjectID;

import com.lakesidemutual.monomorph.id.generated.proto.policyquoteservice.*;

import com.lakesidemutual.monomorph.dto.generated.client.InsuranceQuoteRequestDto;
import com.lakesidemutual.monomorph.dto.generated.proto.insurancequoterequestdto.InsuranceQuoteRequestDtoDTO;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PolicyQuoteService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "policy_management_backend";

    private ManagedChannel businessChannel;
    private PolicyQuoteServiceServiceGrpc.PolicyQuoteServiceServiceBlockingStub businessStub;

    /** Public no-arg constructor matching the original class's implicit constructor. */
    public PolicyQuoteService() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private PolicyQuoteService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = PolicyQuoteServiceServiceGrpc.PolicyQuoteServiceServiceBlockingStub.newBlockingStub(businessChannel);
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
                // ignored
            }
        }
    }

    public static PolicyQuoteService fromID(RefactoredObjectID existingId) {
        return new PolicyQuoteService(existingId);
    }

    // --- Business service methods (only those defined in the proto service) ---

    public void handleCustomerDecision(Long insuranceQuoteRequestId, boolean quoteAccepted, Date decisionDate) {
        ensureRpcSetup();

        Timestamp decisionDateProto = Timestamps.fromMillis(decisionDate.getTime());

        HandleCustomerDecisionRequest request = HandleCustomerDecisionRequest.newBuilder()
                .setObjectId(this.objectId)
                .setInsuranceQuoteRequestId(insuranceQuoteRequestId)
                .setQuoteAccepted(quoteAccepted)
                .setDecisionDate(decisionDateProto)
                .build();

        this.businessStub.handleCustomerDecision(request);
    }

    public void receiveInsuranceQuoteRequest(InsuranceQuoteRequestDto insuranceQuoteRequestDto) {
        ensureRpcSetup();

        InsuranceQuoteRequestDtoDTO protoDto = insuranceQuoteRequestDto.toDTO();

        ReceiveInsuranceQuoteRequestRequest request = ReceiveInsuranceQuoteRequestRequest.newBuilder()
                .setObjectId(this.objectId)
                .setInsuranceQuoteRequestDto(protoDto)
                .build();

        this.businessStub.receiveInsuranceQuoteRequest(request);
    }

    // --- Helper to lazily initialize the gRPC channel/stub if needed ---
    private void ensureRpcSetup() {
        if (businessStub == null || businessChannel == null || businessChannel.isShutdown()) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to setup gRPC channel for PolicyQuoteService", e);
            }
        }
    }
}