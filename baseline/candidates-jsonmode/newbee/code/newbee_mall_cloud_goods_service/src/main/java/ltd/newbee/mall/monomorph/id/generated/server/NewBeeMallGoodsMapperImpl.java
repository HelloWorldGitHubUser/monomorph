package ltd.newbee.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.stocknumdto.StockNumDTODTO;
import ltd.newbee.mall.monomorph.dto.generated.server.StockNumDTOMapper;
import ltd.newbee.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallgoodsmapper.*;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.ServerObjectManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeMallGoodsMapper.
 * - Handles gRPC requests for NewBeeMallGoodsMapper API.
 * - Uses an injected singleton instance of NewBeeMallGoodsMapper.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on the retrieved NewBeeMallGoodsMapper instance.
 */
public class NewBeeMallGoodsMapperImpl extends NewBeeMallGoodsMapperServiceGrpc.NewBeeMallGoodsMapperServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final NewBeeMallGoodsMapper mapper; // singleton mapper instance
    private final String serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());

    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeMallGoodsMapper");

    public NewBeeMallGoodsMapperImpl(LeaseManager leaseManager, NewBeeMallGoodsMapper mapper) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.mapper = Objects.requireNonNull(mapper);
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            // The mapper is a singleton; reuse the same instance ID across calls.
            RefactoredObjectID responseProto = toID(this.mapper, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- ServerObjectManager method implementations ---

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        if (instance == null) {
            throw new IllegalArgumentException("Instance cannot be null");
        }
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            instanceId = UUID.randomUUID().toString();
            boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, clientId);
            if (!registered) {
                throw new RuntimeException("Failed to register new instance ID: " + instanceId);
            }
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
    public NewBeeMallGoodsMapper fromID(RefactoredObjectID id) throws Exception {
        if (id == null || id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + (id == null ? "null" : id.getClassID()));
        }
        NewBeeMallGoodsMapper instance = (NewBeeMallGoodsMapper) leaseManager.getInstance(id.getInstanceID());
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

    // --- gRPC methods defined in the proto file ---

    @Override
    public void recoverStockNum(RecoverStockNumRequest request, StreamObserver<RecoverStockNumResponse> responseObserver) {
        try {
            NewBeeMallGoodsMapper instance = fromID(request.getRefactoredObjectId());
            List<StockNumDTO> stockNumDTOs = new ArrayList<>();
            for (StockNumDTODTO dto : request.getStockNumDtosList()) {
                stockNumDTOs.add(StockNumDTOMapper.INSTANCE.fromDTO(dto));
            }
            int result = instance.recoverStockNum(stockNumDTOs);
            RecoverStockNumResponse response = RecoverStockNumResponse.newBuilder()
                    .setResult(result)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void selectByPrimaryKey(SelectByPrimaryKeyRequest request, StreamObserver<SelectByPrimaryKeyResponse> responseObserver) {
        try {
            NewBeeMallGoodsMapper instance = fromID(request.getRefactoredObjectId());
            NewBeeMallGoods goods = instance.selectByPrimaryKey(request.getGoodsId());
            NewBeeMallGoodsDTO dto = ltd.newbee.mall.monomorph.dto.generated.server.NewBeeMallGoodsMapper.INSTANCE.toDTO(goods);
            SelectByPrimaryKeyResponse response = SelectByPrimaryKeyResponse.newBuilder()
                    .setGoods(dto)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void selectByPrimaryKeys(SelectByPrimaryKeysRequest request, StreamObserver<SelectByPrimaryKeysResponse> responseObserver) {
        try {
            NewBeeMallGoodsMapper instance = fromID(request.getRefactoredObjectId());
            List<Long> goodsIds = new ArrayList<>();
            for (long id : request.getGoodsIdsList()) {
                goodsIds.add(id);
            }
            List<NewBeeMallGoods> goodsList = instance.selectByPrimaryKeys(goodsIds);
            List<NewBeeMallGoodsDTO> dtoList = new ArrayList<>();
            for (NewBeeMallGoods goods : goodsList) {
                dtoList.add(ltd.newbee.mall.monomorph.dto.generated.server.NewBeeMallGoodsMapper.INSTANCE.toDTO(goods));
            }
            SelectByPrimaryKeysResponse response = SelectByPrimaryKeysResponse.newBuilder()
                    .addAllGoodsList(dtoList)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void updateStockNum(UpdateStockNumRequest request, StreamObserver<UpdateStockNumResponse> responseObserver) {
        try {
            NewBeeMallGoodsMapper instance = fromID(request.getRefactoredObjectId());
            List<StockNumDTO> stockNumDTOs = new ArrayList<>();
            for (StockNumDTODTO dto : request.getStockNumDtosList()) {
                stockNumDTOs.add(StockNumDTOMapper.INSTANCE.fromDTO(dto));
            }
            int result = instance.updateStockNum(stockNumDTOs);
            UpdateStockNumResponse response = UpdateStockNumResponse.newBuilder()
                    .setResult(result)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}

