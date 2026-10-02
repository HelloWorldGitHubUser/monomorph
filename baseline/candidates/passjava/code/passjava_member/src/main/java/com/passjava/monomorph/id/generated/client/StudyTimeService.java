package com.passjava.monomorph.id.generated.client;

import com.passjava.monomorph.id.shared.client.AbstractRefactoredClient;
import com.passjava.monomorph.id.generated.helpers.ServiceRegistry;
import com.passjava.monomorph.id.shared.RefactoredObjectID;
import com.passjava.utils.R;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import com.passjava.monomorph.id.generated.proto.studytimeservice.*;
import com.google.protobuf.Value;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return toUtilsR(response);
    }

    private static R toUtilsR(RDTO dto) {
        R result = new R();
        for (RDTO.EntryDTO entry : dto.getEntrySetList()) {
            result.put(entry.getKey(), toJavaObject(entry.getValue()));
        }
        return result;
    }

    private static Object toJavaObject(Value value) {
        if (value == null) {
            return null;
        }
        switch (value.getKindCase()) {
            case NULL_VALUE:
                return null;
            case NUMBER_VALUE:
                return value.getNumberValue();
            case STRING_VALUE:
                return value.getStringValue();
            case BOOL_VALUE:
                return value.getBoolValue();
            case STRUCT_VALUE:
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<String, Value> field : value.getStructValue().getFieldsMap().entrySet()) {
                    map.put(field.getKey(), toJavaObject(field.getValue()));
                }
                return map;
            case LIST_VALUE:
                List<Object> list = new ArrayList<>();
                for (Value item : value.getListValue().getValuesList()) {
                    list.add(toJavaObject(item));
                }
                return list;
            default:
                return null;
        }
    }
}

