package com.youlai.mall.monomorph.id.generated.client;

import com.youlai.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;

import com.youlai.mall.monomorph.id.generated.proto.sysuserservice.*;
import com.youlai.mall.monomorph.dto.generated.proto.userauthinfo.UserAuthInfoDTO;
import com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class SysUserService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "youlai_system";

    private ManagedChannel businessChannel;
    private SysUserServiceServiceGrpc.SysUserServiceServiceBlockingStub businessStub;

    /** Public no-arg constructor. */
    public SysUserService() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private SysUserService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = SysUserServiceServiceGrpc.newBlockingStub(businessChannel);
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

    /** Factory method for creating a proxy for an EXISTING remote object. */
    public static SysUserService fromID(RefactoredObjectID existingId) {
        return new SysUserService(existingId);
    }

    /**
     * Implementation of the getUserAuthInfo RPC method.
     *
     * @param username the username
     * @return client-side proxy UserAuthInfo
     */
    public UserAuthInfo getUserAuthInfo(String username) {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC channel for SysUserService", e);
            }
        }

        GetUserAuthInfoRequest request = GetUserAuthInfoRequest.newBuilder()
                .setObjectId(this.objectId)
                .setUsername(username)
                .build();

        GetUserAuthInfoResponse response = businessStub.getUserAuthInfo(request);
        UserAuthInfoDTO dto = response.getUserAuthInfo();

        return UserAuthInfo.fromDTO(dto);
    }
}