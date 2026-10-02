package com.youlai.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.youlai.mall.monomorph.id.generated.proto.umsmemberservice.*;

import com.youlai.mall.monomorph.id.shared.server.LeaseManager;
import com.youlai.mall.monomorph.id.shared.server.ServerObjectManager;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;

import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.generated.helpers.ClassIdRegistry;

import com.youlai.mall.service.ums.UmsMemberService;

import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO;

import com.youlai.mall.model.ums.dto.MemberRegisterDto;
import com.youlai.mall.model.ums.dto.MemberAuthDTO;
import com.youlai.mall.model.ums.dto.MemberAddressDTO;

import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoMapper;
import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTOMapper;
import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTOMapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for UmsMemberService.
 * - Handles gRPC requests for UmsMemberService API.
 * - Creates and reuses a singleton UmsMemberService instance.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on the retrieved UmsMemberService instance.
 */
public class UmsMemberServiceImpl extends UmsMemberServiceServiceGrpc.UmsMemberServiceServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    // Static identifier for the class type managed by this service
    public static final String CLASS_ID = ClassIdRegistry.getClassId("UmsMemberService");

    // Singleton instance managed by this service
    private volatile UmsMemberService singletonInstance;

    public UmsMemberServiceImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            // 1. Extract client ID. ConstructorArgs is empty for this interface-backed service.
            String clientId = request.getClientID();

            // 2. Retrieve or create the singleton business instance
            UmsMemberService instance = getOrCreateSingletonInstance();

            // 3. Generate / retrieve a RefactoredObjectID for the instance
            RefactoredObjectID responseProto = toID(instance, clientId);

            // 4. Send the response
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- Start of ServerObjectManager method implementations ---

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        // Validate if id instance exists
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            // Generate a new unique instance ID
            instanceId = UUID.randomUUID().toString();
        }

        // Register with LeaseManager
        boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, clientId);
        if (!registered) {
            throw new RuntimeException("Failed to register new instance ID: " + instanceId);
        }

        // Build RefactoredObjectID
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
    public UmsMemberService fromID(RefactoredObjectID id) throws Exception {
        // Validate the class ID
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }

        // Retrieve the instance from LeaseManager
        UmsMemberService instance = (UmsMemberService) leaseManager.getInstance(id.getInstanceID());
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
    // --- End of ServerObjectManager method implementations ---

    // --- Singleton creation logic ---

    private UmsMemberService getOrCreateSingletonInstance() {
        if (singletonInstance == null) {
            synchronized (this) {
                if (singletonInstance == null) {
                    singletonInstance = new com.youlai.mall.service.ums.impl.UmsMemberServiceImpl();
                }
            }
        }
        return singletonInstance;
    }

    // --- Other gRPC methods defined in the proto file ---

    @Override
    public void addProductViewHistory(AddProductViewHistoryRequest request, StreamObserver<AddProductViewHistoryResponse> responseObserver) {
        try {
            // 1. Retrieve the business instance
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            // 2. Convert DTO to client proxy usable by the business service
            ProductHistoryVO productHistoryVO = ProductHistoryVO.fromDTO(request.getProductHistoryVo());

            // 3. Call the business method
            instance.addProductViewHistory(productHistoryVO);

            // 4. Build and send empty response
            responseObserver.onNext(AddProductViewHistoryResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deductBalance(DeductBalanceRequest request, StreamObserver<DeductBalanceResponse> responseObserver) {
        try {
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            instance.deductBalance(request.getMemberId(), request.getAmount());

            responseObserver.onNext(DeductBalanceResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getMemberOpenId(GetMemberOpenIdRequest request, StreamObserver<GetMemberOpenIdResponse> responseObserver) {
        try {
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            String openId = instance.getMemberOpenId(request.getMemberId());

            GetMemberOpenIdResponse response = GetMemberOpenIdResponse.newBuilder()
                    .setOpenId(openId)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void listMemberAddresses(ListMemberAddressesRequest request, StreamObserver<ListMemberAddressesResponse> responseObserver) {
        try {
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            List<MemberAddressDTO> addresses = instance.listMemberAddresses(request.getMemberId());

            ListMemberAddressesResponse.Builder builder = ListMemberAddressesResponse.newBuilder();
            for (MemberAddressDTO address : addresses) {
                builder.addAddresses(MemberAddressDTOMapper.INSTANCE.toDTO(address));
            }

            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void loadUserByMobile(LoadUserByMobileRequest request, StreamObserver<LoadUserByMobileResponse> responseObserver) {
        try {
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            MemberAuthDTO memberAuthDTO = instance.loadUserByMobile(request.getMobile());

            LoadUserByMobileResponse response = LoadUserByMobileResponse.newBuilder()
                    .setMemberAuth(MemberAuthDTOMapper.INSTANCE.toDTO(memberAuthDTO))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void loadUserByOpenId(LoadUserByOpenIdRequest request, StreamObserver<LoadUserByOpenIdResponse> responseObserver) {
        try {
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            MemberAuthDTO memberAuthDTO = instance.loadUserByOpenId(request.getOpenid());

            LoadUserByOpenIdResponse response = LoadUserByOpenIdResponse.newBuilder()
                    .setMemberAuth(MemberAuthDTOMapper.INSTANCE.toDTO(memberAuthDTO))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void registerMember(RegisterMemberRequest request, StreamObserver<RegisterMemberResponse> responseObserver) {
        try {
            UmsMemberService instance = fromID(request.getRefactoredObjectId());

            MemberRegisterDto memberRegisterDto = MemberRegisterDtoMapper.INSTANCE.fromDTO(request.getMemberRegisterDto());

            Long memberId = instance.registerMember(memberRegisterDto);

            RegisterMemberResponse response = RegisterMemberResponse.newBuilder()
                    .setMemberId(memberId)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}