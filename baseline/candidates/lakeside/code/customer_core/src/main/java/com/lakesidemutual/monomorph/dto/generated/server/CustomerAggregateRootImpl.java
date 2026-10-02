package com.lakesidemutual.monomorph.dto.generated.server;

import io.grpc.stub.StreamObserver;

import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.CustomerAggregateRootServiceGrpc;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.UpdateCustomerProfileRequest;
import com.lakesidemutual.monomorph.dto.generated.proto.customeraggregateroot.UpdateCustomerProfileResponse;

import com.lakesidemutual.domain.customer.CustomerAggregateRoot;
import com.lakesidemutual.domain.customer.CustomerProfileEntity;

import com.lakesidemutual.monomorph.dto.generated.proto.customerprofileentity.CustomerProfileEntityDTO;

import com.lakesidemutual.monomorph.dto.generated.server.CustomerAggregateRootMapper;
import com.lakesidemutual.monomorph.dto.generated.server.CustomerProfileEntityMapper;

/**
 * gRPC Service implementation for CustomerAggregateRoot.
 * Handles gRPC requests for CustomerAggregateRoot API.
 * Interacts with Mapper for switching between DTO and CustomerAggregateRoot instances.
 */
public class CustomerAggregateRootImpl extends CustomerAggregateRootServiceGrpc.CustomerAggregateRootServiceImplBase {

    /**
     * Implements the updateCustomerProfile RPC.
     * Maps the incoming DTOs to domain objects, calls the business logic,
     * and returns the updated aggregate root as a DTO.
     */
    @Override
    public void updateCustomerProfile(UpdateCustomerProfileRequest request,
                                      StreamObserver<UpdateCustomerProfileResponse> responseObserver) {
        // 1. Map the aggregate DTO to the domain object.
        CustomerAggregateRoot original =
                CustomerAggregateRootMapper.INSTANCE.fromDTO(request.getDto());

        // 2. Map the updated customer profile DTO to the domain object.
        CustomerProfileEntity updatedProfile =
                CustomerProfileEntityMapper.INSTANCE.fromDTO(request.getUpdatedCustomerProfile());

        // 3. Perform the business logic on the domain object.
        original.updateCustomerProfile(updatedProfile);

        // 4. Map the modified domain object back to a DTO.
        CustomerAggregateRootDTO responseDto =
                CustomerAggregateRootMapper.INSTANCE.toDTO(original);

        // 5. Build and send the response.
        UpdateCustomerProfileResponse response =
                UpdateCustomerProfileResponse.newBuilder()
                        .setDto(responseDto)
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}