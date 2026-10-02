package com.goodskill.monomorph.id.generated.client;

import com.goodskill.monomorph.id.shared.client.AbstractRefactoredClient;
import com.goodskill.monomorph.id.generated.helpers.ServiceRegistry;
import com.goodskill.monomorph.id.shared.RefactoredObjectID;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.*;
import com.goodskill.monomorph.dto.generated.client.OrderDTO;
import com.goodskill.monomorph.dto.generated.proto.orderdto.OrderDTODTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class OrderServiceImpl extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "goodskill_order";

    private ManagedChannel businessChannel;
    private OrderServiceImplServiceGrpc.OrderServiceImplServiceBlockingStub businessStub;

    /** Public no-arg constructor matching the original OrderServiceImpl API. */
    public OrderServiceImpl() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private OrderServiceImpl(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected synchronized void performRpcSetup() throws Exception {
        if (this.businessStub != null) {
            return;
        }

        // Avoid leaking a previously opened channel if setup is re-entered.
        if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
            this.businessChannel.shutdownNow();
        }

        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        if (endpoint == null) {
            throw new IllegalStateException("No gRPC endpoint registered for service ID: " + TARGET_SERVICE_ID);
        }

        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = OrderServiceImplServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        if (this.businessStub == null) {
            performRpcSetup();
        }

        // The generated client currently exposes a no-arg constructor only, so a default
        // ConstructorArgs instance is sent. If constructor arguments are later supported,
        // map the args array to ConstructorArgs here.
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.getDefaultInstance())
                .build();

        return this.businessStub.createObject(createRequest);
    }

    @Override
    protected synchronized void performSubclassRpcCleanup() {
        ManagedChannel channel = this.businessChannel;
        this.businessChannel = null;
        this.businessStub = null;

        if (channel == null || channel.isShutdown()) {
            return;
        }

        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            if (!channel.isTerminated()) {
                channel.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            channel.shutdownNow();
        }
    }

    /** Factory method for creating a proxy from an EXISTING ID. */
    public static OrderServiceImpl fromID(RefactoredObjectID existingId) {
        return new OrderServiceImpl(existingId);
    }

    // --- Service methods defined in the generated proto ---

    public Long count(long seckillId) {
        ensureBusinessRpcSetup();
        CountRequest request = CountRequest.newBuilder()
                .setObjectId(this.objectId)
                .setSeckillId(seckillId)
                .build();
        CountResponse response = this.businessStub.count(request);
        return response.getCount();
    }

    public Boolean deleteRecord(long seckillId) {
        ensureBusinessRpcSetup();
        DeleteRecordRequest request = DeleteRecordRequest.newBuilder()
                .setObjectId(this.objectId)
                .setSeckillId(seckillId)
                .build();
        DeleteRecordResponse response = this.businessStub.deleteRecord(request);
        return response.getSuccess();
    }

    public String saveRecord(OrderDTO orderDTO) {
        ensureBusinessRpcSetup();
        OrderDTODTO protoOrder = orderDTO.toDTO();
        SaveRecordRequest request = SaveRecordRequest.newBuilder()
                .setObjectId(this.objectId)
                .setOrderDto(protoOrder)
                .build();
        SaveRecordResponse response = this.businessStub.saveRecord(request);
        return response.getOrderId();
    }

    private void ensureBusinessRpcSetup() {
        if (this.businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to initialise gRPC channel for OrderServiceImpl", e);
            }
        }
    }
}
