package com.youlai.mall.monomorph.id.generated.client;

import com.youlai.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;

import com.youlai.mall.monomorph.id.generated.proto.umsmemberservice.*;
import com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.ProductHistoryVODTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTODTO;
import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;

import com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO;
import com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto;
import com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO;
import com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UmsMemberService extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "mall_ums";

    private ManagedChannel businessChannel;
    private UmsMemberServiceServiceGrpc.UmsMemberServiceServiceBlockingStub businessStub;

    public UmsMemberService() {
        initialize();
    }

    private UmsMemberService(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = UmsMemberServiceServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();
        CreateObjectRequest.Builder requestBuilder = CreateObjectRequest.newBuilder()
                .setClientID(clientId);
        // ConstructorArgs is empty because the original type is an interface
        requestBuilder.setConstructorArgs(ConstructorArgs.newBuilder().build());
        CreateObjectRequest createRequest = requestBuilder.build();
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
                // ignore
            }
        }
    }

    public static UmsMemberService fromID(RefactoredObjectID existingId) {
        return new UmsMemberService(existingId);
    }

    // --- Service Methods ---

    public void addProductViewHistory(ProductHistoryVO product) {
        ProductHistoryVODTO protoDto = product.toDTO();
        AddProductViewHistoryRequest request = AddProductViewHistoryRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setProductHistoryVo(protoDto)
                .build();
        this.businessStub.addProductViewHistory(request);
    }

    public void deductBalance(Long memberId, Long amount) {
        DeductBalanceRequest request = DeductBalanceRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberId(memberId)
                .setAmount(amount)
                .build();
        this.businessStub.deductBalance(request);
    }

    public String getMemberOpenId(Long memberId) {
        GetMemberOpenIdRequest request = GetMemberOpenIdRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberId(memberId)
                .build();
        GetMemberOpenIdResponse response = this.businessStub.getMemberOpenId(request);
        return response.getOpenId();
    }

    public List<MemberAddressDTO> listMemberAddresses(Long memberId) {
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
        LoadUserByMobileRequest request = LoadUserByMobileRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMobile(mobile)
                .build();
        LoadUserByMobileResponse response = this.businessStub.loadUserByMobile(request);
        return MemberAuthDTO.fromDTO(response.getMemberAuth());
    }

    public MemberAuthDTO loadUserByOpenId(String openid) {
        LoadUserByOpenIdRequest request = LoadUserByOpenIdRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setOpenid(openid)
                .build();
        LoadUserByOpenIdResponse response = this.businessStub.loadUserByOpenId(request);
        return MemberAuthDTO.fromDTO(response.getMemberAuth());
    }

    public Long registerMember(MemberRegisterDto dto) {
        MemberRegisterDtoDTO protoDto = dto.toDTO();
        RegisterMemberRequest request = RegisterMemberRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setMemberRegisterDto(protoDto)
                .build();
        RegisterMemberResponse response = this.businessStub.registerMember(request);
        return response.getMemberId();
    }
}