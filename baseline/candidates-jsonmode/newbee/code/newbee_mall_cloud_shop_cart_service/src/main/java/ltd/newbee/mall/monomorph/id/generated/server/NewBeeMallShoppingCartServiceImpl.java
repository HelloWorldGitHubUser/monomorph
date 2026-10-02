package ltd.newbee.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallshoppingcartservice.*;
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.ServerObjectManager;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import ltd.newbee.mall.service.NewBeeMallShoppingCartService;
import ltd.newbee.mall.api.mall.vo.NewBeeMallShoppingCartItemVO;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallshoppingcartitemvo.NewBeeMallShoppingCartItemVODTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallshoppingcartitemvo.NewBeeMallShoppingCartItemVOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeMallShoppingCartService.
 * - Handles gRPC requests for NewBeeMallShoppingCartService API.
 * - Creates transient NewBeeMallShoppingCartService instances.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on retrieved NewBeeMallShoppingCartService instances.
 */
public class NewBeeMallShoppingCartServiceImpl
        extends NewBeeMallShoppingCartServiceServiceGrpc.NewBeeMallShoppingCartServiceServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    // Static identifier for the class type managed by this service
    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeMallShoppingCartService");

    public NewBeeMallShoppingCartServiceImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            // ConstructorArgs is empty; no argument mapping needed.

            // Create a new instance of the concrete business implementation.
            NewBeeMallShoppingCartService newInstance =
                    new ltd.newbee.mall.service.impl.NewBeeMallShoppingCartServiceImpl();

            // Register the instance and obtain its RefactoredObjectID.
            RefactoredObjectID responseProto = toID(newInstance, clientId);

            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- getCartItemsForSettle gRPC Method Implementation ---

    @Override
    public void getCartItemsForSettle(GetCartItemsForSettleRequest request,
                                      StreamObserver<GetCartItemsForSettleResponse> responseObserver) {
        try {
            RefactoredObjectID refactoredObjectId = request.getRefactoredObjectId();
            NewBeeMallShoppingCartService instance = fromID(refactoredObjectId);

            List<Long> cartItemIds = request.getCartItemIdsList();
            Long newBeeMallUserId = request.getNewBeeMallUserId();

            List<NewBeeMallShoppingCartItemVO> vos =
                    instance.getCartItemsForSettle(cartItemIds, newBeeMallUserId);

            GetCartItemsForSettleResponse.Builder responseBuilder =
                    GetCartItemsForSettleResponse.newBuilder();

            // Convert each VO to its corresponding DTO using the generated mapper.
            if (vos != null) {
                for (NewBeeMallShoppingCartItemVO vo : vos) {
                    NewBeeMallShoppingCartItemVODTO dto =
                            NewBeeMallShoppingCartItemVOMapper.INSTANCE.toDTO(vo);
                    responseBuilder.addItems(dto);
                }
            }

            responseObserver.onNext(responseBuilder.build());
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
        boolean registered = leaseManager.registerInstanceAndGrantLease(
                instanceId, CLASS_ID, instance, clientId);
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
    public NewBeeMallShoppingCartService fromID(RefactoredObjectID id) throws Exception {
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException(
                    "class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }
        NewBeeMallShoppingCartService instance =
                (NewBeeMallShoppingCartService) leaseManager.getInstance(id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id);
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
}
