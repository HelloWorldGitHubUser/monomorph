package com.youlai.mall.monomorph.id.generated.client;

import com.youlai.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.generated.proto.umsmemberservice.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// DTO proxy classes
import com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO;
import com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO;
import com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto;
import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVOMapper;

// Proto DTO classes
import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTODTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.ProductHistoryVODTO;

// Original model class (still available)
import com.youlai.mall.model.pms.vo.ProductHistoryVO;

/**
 * gRPC client for UmsMemberService.
 * Provides transparent remote method invocation while maintaining the original method signatures
 * where possible.
 */
public class UmsMemberService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "mall_ums";

    private ManagedChannel businessChannel;
    private UmsMemberServiceServiceGrpc.UmsMemberServiceServiceBlockingStub businessStub;

    /** Public no-arg constructor for creating a new remote object. */
    public UmsMemberService() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private UmsMemberService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder
                .forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = UmsMemberServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        // No constructor arguments for the original interface.
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
                // Restore interrupt flag
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public static UmsMemberService fromID(RefactoredObjectID existingId) {
        return new UmsMemberService(existingId);
    }

    private void ensureRpcSetup() {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC stub", e);
            }
        }
    }

    // --- Service methods ---

    public void addProductViewHistory(ProductHistoryVO product) {
        ensureRpcSetup();
        AddProductViewHistoryRequest request = AddProductViewHistoryRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setProductHistoryVo(ProductHistoryVOMapper.INSTANCE.toDTO(product))
                .build();
        this.businessStub.addProductViewHistory(request);
    }

    public void deductBalance(Long memberId, Long amount) {
        ensureRpcSetup();
        DeductBalanceRequest request = DeductBalanceRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberId(memberId)
                .setAmount(amount)
                .build();
        this.businessStub.deductBalance(request);
    }

    public String getMemberOpenId(Long memberId) {
        ensureRpcSetup();
        GetMemberOpenIdRequest request = GetMemberOpenIdRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberId(memberId)
                .build();
        GetMemberOpenIdResponse response = this.businessStub.getMemberOpenId(request);
        return response.getOpenId();
    }

    public List<MemberAddressDTO> listMemberAddresses(Long memberId) {
        ensureRpcSetup();
        ListMemberAddressesRequest request = ListMemberAddressesRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberId(memberId)
                .build();
        ListMemberAddressesResponse response = this.businessStub.listMemberAddresses(request);

        List<MemberAddressDTO> result = new ArrayList<>();
        for (MemberAddressDTODTO dto : response.getAddressesList()) {
            result.add(MemberAddressDTO.fromDTO(dto));
        }
        return result;
    }

    public MemberAuthDTO loadUserByMobile(String mobile) {
        ensureRpcSetup();
        LoadUserByMobileRequest request = LoadUserByMobileRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMobile(mobile)
                .build();
        LoadUserByMobileResponse response = this.businessStub.loadUserByMobile(request);
        return MemberAuthDTO.fromDTO(response.getMemberAuth());
    }

    public MemberAuthDTO loadUserByOpenId(String openid) {
        ensureRpcSetup();
        LoadUserByOpenIdRequest request = LoadUserByOpenIdRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setOpenid(openid)
                .build();
        LoadUserByOpenIdResponse response = this.businessStub.loadUserByOpenId(request);
        return MemberAuthDTO.fromDTO(response.getMemberAuth());
    }

    public Long registerMember(MemberRegisterDto dto) {
        ensureRpcSetup();
        MemberRegisterDtoDTO dtoProto = dto.toDTO();
        RegisterMemberRequest request = RegisterMemberRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberRegisterDto(dtoProto)
                .build();
        RegisterMemberResponse response = this.businessStub.registerMember(request);
        return response.getMemberId();
    }
}