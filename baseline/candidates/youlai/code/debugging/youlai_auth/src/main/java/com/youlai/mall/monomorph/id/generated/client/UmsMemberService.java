package com.youlai.mall.monomorph.id.generated.client;

import com.youlai.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;

import com.youlai.mall.monomorph.id.generated.proto.umsmemberservice.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO;
import com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto;
import com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO;
import com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO;

import com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.ProductHistoryVODTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTODTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;

public class UmsMemberService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "mall_ums";

    private ManagedChannel businessChannel;
    private UmsMemberServiceServiceGrpc.UmsMemberServiceServiceBlockingStub businessStub;

    /**
     * Public no-arg constructor.
     * The original interface has no constructor parameters, so no additional
     * arguments are required for initialization.
     */
    public UmsMemberService() {
        initialize();
    }

    /**
     * Private constructor used by the fromID factory.
     */
    private UmsMemberService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = UmsMemberServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        if (businessStub == null) {
            performRpcSetup();
        }

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

    /**
     * Factory method for creating proxy from an EXISTING ID.
     */
    public static UmsMemberService fromID(RefactoredObjectID existingId) {
        return new UmsMemberService(existingId);
    }

    /**
     * Ensures that the gRPC stub has been initialized before performing a call.
     */
    private void ensureRpcSetup() throws Exception {
        if (businessStub == null) {
            performRpcSetup();
        }
    }

    // --- Service Methods ---

    public void addProductViewHistory(ProductHistoryVO product) {
        try {
            ensureRpcSetup();
            AddProductViewHistoryRequest request = AddProductViewHistoryRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setProductHistoryVo(product.toDTO())
                    .build();
            businessStub.addProductViewHistory(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke addProductViewHistory", e);
        }
    }

    public void deductBalance(Long memberId, Long amount) {
        try {
            ensureRpcSetup();
            DeductBalanceRequest request = DeductBalanceRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setMemberId(memberId)
                    .setAmount(amount)
                    .build();
            businessStub.deductBalance(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke deductBalance", e);
        }
    }

    public String getMemberOpenId(Long memberId) {
        try {
            ensureRpcSetup();
            GetMemberOpenIdRequest request = GetMemberOpenIdRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setMemberId(memberId)
                    .build();
            GetMemberOpenIdResponse response = businessStub.getMemberOpenId(request);
            return response.getOpenId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke getMemberOpenId", e);
        }
    }

    public List<MemberAddressDTO> listMemberAddresses(Long memberId) {
        try {
            ensureRpcSetup();
            ListMemberAddressesRequest request = ListMemberAddressesRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setMemberId(memberId)
                    .build();
            ListMemberAddressesResponse response = businessStub.listMemberAddresses(request);

            List<MemberAddressDTO> result = new ArrayList<>();
            for (MemberAddressDTODTO dto : response.getAddressesList()) {
                result.add(MemberAddressDTO.fromDTO(dto));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke listMemberAddresses", e);
        }
    }

    public MemberAuthDTO loadUserByMobile(String mobile) {
        try {
            ensureRpcSetup();
            LoadUserByMobileRequest request = LoadUserByMobileRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setMobile(mobile)
                    .build();
            LoadUserByMobileResponse response = businessStub.loadUserByMobile(request);
            return MemberAuthDTO.fromDTO(response.getMemberAuth());
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke loadUserByMobile", e);
        }
    }

    public MemberAuthDTO loadUserByOpenId(String openid) {
        try {
            ensureRpcSetup();
            LoadUserByOpenIdRequest request = LoadUserByOpenIdRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setOpenid(openid)
                    .build();
            LoadUserByOpenIdResponse response = businessStub.loadUserByOpenId(request);
            return MemberAuthDTO.fromDTO(response.getMemberAuth());
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke loadUserByOpenId", e);
        }
    }

    public Long registerMember(MemberRegisterDto dto) {
        try {
            ensureRpcSetup();
            RegisterMemberRequest request = RegisterMemberRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setMemberRegisterDto(dto.toDTO())
                    .build();
            RegisterMemberResponse response = businessStub.registerMember(request);
            return response.getMemberId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke registerMember", e);
        }
    }
}