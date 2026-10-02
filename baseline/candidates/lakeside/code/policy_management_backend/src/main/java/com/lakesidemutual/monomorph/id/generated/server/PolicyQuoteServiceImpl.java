package com.lakesidemutual.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.lakesidemutual.monomorph.id.generated.proto.policyquoteservice.*;
import com.lakesidemutual.monomorph.id.shared.server.LeaseManager;
import com.lakesidemutual.monomorph.id.shared.server.ServerObjectManager;
import com.lakesidemutual.monomorph.id.shared.RefactoredObjectID;
import com.lakesidemutual.monomorph.id.generated.helpers.ServiceRegistry;
import com.lakesidemutual.monomorph.id.generated.helpers.ClassIdRegistry;
import com.lakesidemutual.application.PolicyQuoteService;
import com.lakesidemutual.interfaces.dtos.policy.insurancequoterequest.InsuranceQuoteRequestDto;
import com.lakesidemutual.monomorph.dto.generated.proto.insurancequoterequestdto.InsuranceQuoteRequestDtoDTO;
import com.lakesidemutual.monomorph.dto.generated.mapper.InsuranceQuoteRequestDtoMapper;
import com.google.protobuf.Timestamp;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for PolicyQuoteService.
 * - Handles gRPC requests for PolicyQuoteService API.
 * - Creates transient PolicyQuoteService instances.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on retrieved PolicyQuoteService instances.
 */
public class PolicyQuoteServiceImpl extends PolicyQuoteServiceServiceGrpc.PolicyQuoteServiceServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    // Static identifier for the class type managed by this service
    public static final String CLASS_ID = ClassIdRegistry.getClassId("PolicyQuoteService");

    public PolicyQuoteServiceImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            // ConstructorArgs is empty because PolicyQuoteService has no explicit constructor arguments.
            PolicyQuoteService newInstance = new PolicyQuoteService();

            RefactoredObjectID responseProto = toID(newInstance, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- ServerObjectManager method implementations ---

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            instanceId = UUID.randomUUID().toString();
        }

        boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, clientId);
        if (!registered) {
            throw new RuntimeException("Failed to register new instance ID: " + instanceId);
        }

        return RefactoredObjectID.newBuilder()
                .setInstanceID(instanceId)
                .setClassID(CLASS_ID)
                .setServiceID(this.serviceId)
                .build();
    }

    @Override
    public RefactoredObjectID toID(Object instance) throws Exception {
        return toID(instance, serviceId);
    }

    @Override
    public PolicyQuoteService fromID(RefactoredObjectID id) throws Exception {
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }

        PolicyQuoteService instance = (PolicyQuoteService) leaseManager.getInstance(id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id.getInstanceID());
        }
        return instance;
    }

    @Override
    public String getManagedClassId() {
        return CLASS_ID;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    // --- Business gRPC method implementations ---

    @Override
    public void handleCustomerDecision(HandleCustomerDecisionRequest request, StreamObserver<HandleCustomerDecisionResponse> responseObserver) {
        try {
            PolicyQuoteService instance = fromID(request.getObjectId());
            instance.handleCustomerDecision(
                    request.getInsuranceQuoteRequestId(),
                    request.getQuoteAccepted(),
                    toDate(request.getDecisionDate())
            );

            responseObserver.onNext(HandleCustomerDecisionResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void receiveInsuranceQuoteRequest(ReceiveInsuranceQuoteRequestRequest request, StreamObserver<ReceiveInsuranceQuoteRequestResponse> responseObserver) {
        try {
            PolicyQuoteService instance = fromID(request.getObjectId());

            InsuranceQuoteRequestDtoDTO dto = request.getInsuranceQuoteRequestDto();
            InsuranceQuoteRequestDto domainDto = InsuranceQuoteRequestDtoMapper.INSTANCE.fromDTO(dto);
            instance.receiveInsuranceQuoteRequest(domainDto);

            responseObserver.onNext(ReceiveInsuranceQuoteRequestResponse.newBuilder().build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- Helper methods ---

    private java.util.Date toDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        long millis = timestamp.getSeconds() * 1000L + timestamp.getNanos() / 1000000L;
        return new java.util.Date(millis);
    }
}