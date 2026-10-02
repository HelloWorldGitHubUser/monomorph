package com.goodskill.monomorph.id.generated.client;

import com.goodskill.monomorph.dto.generated.proto.orderdto.OrderDTODTO;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.CountRequest;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.CountResponse;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.CreateObjectRequest;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.DeleteRecordRequest;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.DeleteRecordResponse;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.OrderServiceImplServiceGrpc;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.SaveRecordRequest;
import com.goodskill.monomorph.id.generated.proto.orderserviceimpl.SaveRecordResponse;
import com.goodskill.monomorph.id.shared.RefactoredObjectID;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * gRPC client for OrderServiceImplService.
 *
 * <p>Wraps the generated blocking stub and exposes one Java method per RPC,
 * handling protobuf request construction and response unwrapping.</p>
 */
public class OrderServiceImplClient implements AutoCloseable {

    private final ManagedChannel channel;
    private final OrderServiceImplServiceGrpc.OrderServiceImplServiceBlockingStub blockingStub;

    public OrderServiceImplClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    public OrderServiceImplClient(ManagedChannel channel) {
        this.channel = Objects.requireNonNull(channel, "channel");
        this.blockingStub = OrderServiceImplServiceGrpc.newBlockingStub(channel);
    }

    public RefactoredObjectID createObject(String clientId) {
        CreateObjectRequest request = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .build();
        return blockingStub.createObject(request);
    }

    public long count(RefactoredObjectID objectId, long seckillId) {
        CountRequest request = CountRequest.newBuilder()
                .setObjectId(objectId)
                .setSeckillId(seckillId)
                .build();
        CountResponse response = blockingStub.count(request);
        return response.getCount();
    }

    public boolean deleteRecord(RefactoredObjectID objectId, long seckillId) {
        DeleteRecordRequest request = DeleteRecordRequest.newBuilder()
                .setObjectId(objectId)
                .setSeckillId(seckillId)
                .build();
        DeleteRecordResponse response = blockingStub.deleteRecord(request);
        return response.getSuccess();
    }

    public String saveRecord(RefactoredObjectID objectId, OrderDTODTO orderDto) {
        SaveRecordRequest request = SaveRecordRequest.newBuilder()
                .setObjectId(objectId)
                .setOrderDto(orderDto)
                .build();
        SaveRecordResponse response = blockingStub.saveRecord(request);
        return response.getOrderId();
    }

    @Override
    public void close() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}