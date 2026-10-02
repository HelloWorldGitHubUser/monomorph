package com.youlai.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.youlai.mall.monomorph.id.generated.proto.skuservice.*;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;
import com.youlai.mall.monomorph.id.shared.server.LeaseManager;
import com.youlai.mall.monomorph.id.shared.server.ServerObjectManager;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import com.youlai.mall.service.pms.SkuService;
import com.youlai.mall.model.pms.dto.SkuInfoDTO;
import com.youlai.mall.model.pms.dto.LockSkuDTO;
import com.youlai.mall.monomorph.dto.generated.proto.skuinfodto.SkuInfoDTODTO;
import com.youlai.mall.monomorph.dto.generated.proto.lockskudto.LockSkuDTODTO;
import com.youlai.mall.monomorph.dto.generated.mapper.SkuInfoDTOMapper;
import com.youlai.mall.monomorph.dto.generated.mapper.LockSkuDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.UUID;

/**
 * gRPC Service implementation for SkuService.
 * - Handles gRPC requests for SkuService API.
 * - Retrieves/creates SkuService instances using ServiceLoader.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on retrieved SkuService instances.
 */
public class SkuServiceImpl extends SkuServiceServiceGrpc.SkuServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("SkuService");

    public SkuServiceImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            SkuService instance = loadSkuService();

            RefactoredObjectID responseProto = toID(instance, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getSkuInfo(GetSkuInfoRequest request, StreamObserver<GetSkuInfoResponse> responseObserver) {
        try {
            SkuService instance = fromID(request.getRefactoredObjectId());
            SkuInfoDTO skuInfoDTO = instance.getSkuInfo(request.getSkuId());
            SkuInfoDTODTO skuInfoProto = SkuInfoDTOMapper.INSTANCE.toDTO(skuInfoDTO);
            GetSkuInfoResponse response = GetSkuInfoResponse.newBuilder()
                    .setSkuInfo(skuInfoProto)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getSkuInfoList(GetSkuInfoListRequest request, StreamObserver<GetSkuInfoListResponse> responseObserver) {
        try {
            SkuService instance = fromID(request.getRefactoredObjectId());
            List<Long> skuIds = request.getSkuIdsList();
            List<SkuInfoDTO> skuInfoDTOs = instance.getSkuInfoList(skuIds);
            List<SkuInfoDTODTO> skuInfoProtos = new ArrayList<>();
            for (SkuInfoDTO dto : skuInfoDTOs) {
                skuInfoProtos.add(SkuInfoDTOMapper.INSTANCE.toDTO(dto));
            }
            GetSkuInfoListResponse response = GetSkuInfoListResponse.newBuilder()
                    .addAllSkuInfos(skuInfoProtos)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void lockStock(LockStockRequest request, StreamObserver<LockStockResponse> responseObserver) {
        try {
            SkuService instance = fromID(request.getRefactoredObjectId());
            String orderToken = request.getOrderToken();
            List<LockSkuDTODTO> lockSkuProtos = request.getLockSkuListList();
            List<LockSkuDTO> lockSkuDTOs = new ArrayList<>();
            for (LockSkuDTODTO proto : lockSkuProtos) {
                lockSkuDTOs.add(LockSkuDTOMapper.INSTANCE.fromDTO(proto));
            }
            boolean success = instance.lockStock(orderToken, lockSkuDTOs);
            LockStockResponse response = LockStockResponse.newBuilder()
                    .setSuccess(success)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void unlockStock(UnlockStockRequest request, StreamObserver<UnlockStockResponse> responseObserver) {
        try {
            SkuService instance = fromID(request.getRefactoredObjectId());
            boolean success = instance.unlockStock(request.getOrderSn());
            UnlockStockResponse response = UnlockStockResponse.newBuilder()
                    .setSuccess(success)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deductStock(DeductStockRequest request, StreamObserver<DeductStockResponse> responseObserver) {
        try {
            SkuService instance = fromID(request.getRefactoredObjectId());
            boolean success = instance.deductStock(request.getOrderSn());
            DeductStockResponse response = DeductStockResponse.newBuilder()
                    .setSuccess(success)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

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
        return toID(instance, this.serviceId);
    }

    @Override
    public SkuService fromID(RefactoredObjectID id) throws Exception {
        if (id == null || id.getClassID() == null || !CLASS_ID.equals(id.getClassID())) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + (id == null ? "null" : id.getClassID()));
        }
        if (id.getServiceID() != null && !id.getServiceID().isEmpty() && !this.serviceId.equals(id.getServiceID())) {
            throw new IllegalArgumentException("service ID mismatch: expected " + this.serviceId + ", got " + id.getServiceID());
        }
        Object instance = leaseManager.getInstance(id.getInstanceID());
        if (!(instance instanceof SkuService)) {
            throw new IllegalArgumentException("No SkuService instance found for ID: " + id.getInstanceID());
        }
        return (SkuService) instance;
    }

    @Override
    public String getManagedClassId() {
        return CLASS_ID;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    private SkuService loadSkuService() {
        return ServiceLoader.load(SkuService.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No implementation found for SkuService. " +
                        "Please provide a ServiceLoader provider or configure dependency injection."));
    }
}
