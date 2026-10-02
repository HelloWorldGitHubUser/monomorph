package com.goodskill.monomorph.dto.generated.client;

import com.goodskill.monomorph.dto.generated.proto.seckillmockresponselistener.HandleSeckillResultRequest;
import com.goodskill.monomorph.dto.generated.proto.seckillmockresponselistener.SeckillMockResponseListenerDTO;
import com.goodskill.monomorph.dto.generated.proto.seckillmockresponselistener.SeckillMockResponseListenerServiceGrpc;
import com.goodskill.monomorph.dto.generated.proto.seckillmockresponsedto.SeckillMockResponseDTODTO;
import com.goodskill.monomorph.id.generated.helpers.ServiceRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;

/**
 * Auto-generated DTO gRPC client for {@code SeckillMockResponseListener} and
 * {@code SeckillMockResponseListenerDTO}.
 */
public class SeckillMockResponseListener implements AutoCloseable {
    private volatile SeckillMockResponseListenerDTO dtoInstance;
    private final Object dtoLock = new Object();

    public SeckillMockResponseListener() {
        this.dtoInstance = SeckillMockResponseListenerDTO.getDefaultInstance();
    }

    private SeckillMockResponseListener(SeckillMockResponseListenerDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null
                ? SeckillMockResponseListenerDTO.getDefaultInstance()
                : dtoInstance;
    }

    // mapping methods
    public SeckillMockResponseListenerDTO toDTO() {
        return this.dtoInstance;
    }

    public static SeckillMockResponseListener fromDTO(SeckillMockResponseListenerDTO dtoInstance) {
        return new SeckillMockResponseListener(dtoInstance);
    }

    // implementation of the gRPC exposed methods

    private static final String TARGET_SERVICE_ID = "goodskill_web";

    private volatile ManagedChannel businessChannel;
    private volatile SeckillMockResponseListenerServiceGrpc.SeckillMockResponseListenerServiceBlockingStub businessStub;
    private volatile boolean closed;

    private final Object rpcSetupLock = new Object();

    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        SeckillMockResponseListenerServiceGrpc.SeckillMockResponseListenerServiceBlockingStub stub =
                SeckillMockResponseListenerServiceGrpc.newBlockingStub(channel);
        this.businessChannel = channel;
        this.businessStub = stub;
    }

    protected void performSubclassRpcCleanup() {
        synchronized (rpcSetupLock) {
            shutdownChannelLocked();
        }
    }

    @Override
    public void close() {
        synchronized (rpcSetupLock) {
            if (!closed) {
                closed = true;
                shutdownChannelLocked();
            }
        }
    }

    private void shutdownChannelLocked() {
        ManagedChannel channel = businessChannel;
        if (channel == null) {
            return;
        }
        try {
            if (!channel.isShutdown()) {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }
            if (!channel.isTerminated()) {
                channel.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            channel.shutdownNow();
        } finally {
            businessChannel = null;
            businessStub = null;
        }
    }

    private void ensureRpcSetup() {
        if (closed) {
            throw new IllegalStateException("gRPC client is already closed");
        }
        if (businessStub == null) {
            synchronized (rpcSetupLock) {
                if (closed) {
                    throw new IllegalStateException("gRPC client is already closed");
                }
                if (businessStub == null) {
                    try {
                        performRpcSetup();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to initialize gRPC client", e);
                    }
                }
            }
        }
    }

    /**
     * Original method: void handleSeckillResult(com.goodskill.dto.SeckillMockResponseDTO).
     * DTO-mapped parameter: SeckillMockResponseDTODTO.
     */
    public void handleSeckillResult(SeckillMockResponseDTODTO responseDto) {
        if (responseDto == null) {
            throw new IllegalArgumentException("responseDto must not be null");
        }
        ensureRpcSetup();

        SeckillMockResponseListenerDTO listenerDto = SeckillMockResponseListenerDTO.newBuilder()
                .setNote(responseDto.getNote())
                .setSeckillId(responseDto.getSeckillId())
                .setStatus(responseDto.getStatus())
                .setTaskId(responseDto.getTaskId())
                .build();

        synchronized (dtoLock) {
            this.dtoInstance = listenerDto;
        }

        HandleSeckillResultRequest request = HandleSeckillResultRequest.newBuilder()
                .setDto(listenerDto)
                .build();

        SeckillMockResponseListenerServiceGrpc.SeckillMockResponseListenerServiceBlockingStub stub = businessStub;
        if (stub == null) {
            throw new IllegalStateException("gRPC client is closed or not initialized");
        }
        stub.handleSeckillResult(request);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public String getNote() {
        return dtoInstance.getNote();
    }

    public void setNote(String note) {
        synchronized (dtoLock) {
            dtoInstance = dtoInstance.toBuilder().setNote(note).build();
        }
    }

    public long getSeckillId() {
        return dtoInstance.getSeckillId();
    }

    public void setSeckillId(long seckillId) {
        synchronized (dtoLock) {
            dtoInstance = dtoInstance.toBuilder().setSeckillId(seckillId).build();
        }
    }

    public boolean getStatus() {
        return dtoInstance.getStatus();
    }

    public void setStatus(boolean status) {
        synchronized (dtoLock) {
            dtoInstance = dtoInstance.toBuilder().setStatus(status).build();
        }
    }

    public String getTaskId() {
        return dtoInstance.getTaskId();
    }

    public void setTaskId(String taskId) {
        synchronized (dtoLock) {
            dtoInstance = dtoInstance.toBuilder().setTaskId(taskId).build();
        }
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}
