package com.lakesidemutual.monomorph.dto.generated.server;

import com.lakesidemutual.domain.customer.CityLookupService;
import com.lakesidemutual.monomorph.dto.generated.proto.citylookupservice.CityLookupServiceDTO;
import com.lakesidemutual.monomorph.dto.generated.proto.citylookupservice.CityLookupServiceServiceGrpc;
import com.lakesidemutual.monomorph.dto.generated.proto.citylookupservice.GetCitiesForPostalCodeRequest;
import com.lakesidemutual.monomorph.dto.generated.proto.citylookupservice.GetCitiesForPostalCodeResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.List;

/**
 * gRPC service implementation for CityLookupService.
 *
 * <p>Handles gRPC requests for city lookup operations and delegates the business
 * logic to the domain layer via a MapStruct mapper.</p>
 */
public class CityLookupServiceImpl extends CityLookupServiceServiceGrpc.CityLookupServiceServiceImplBase {

    @Override
    public void getCitiesForPostalCode(GetCitiesForPostalCodeRequest request,
                                       StreamObserver<GetCitiesForPostalCodeResponse> responseObserver) {
        if (request == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("request must not be null")
                    .asRuntimeException());
            return;
        }

        CityLookupServiceDTO requestDto = request.getDto();
        if (requestDto == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("dto must not be null")
                    .asRuntimeException());
            return;
        }

        String postalCode = requestDto.getPostalCode();
        if (postalCode == null || postalCode.trim().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("postalCode must not be blank")
                    .asRuntimeException());
            return;
        }

        try {
            // Step 1: Map incoming DTO to domain object using MapStruct mapper.
            CityLookupService domainService = CityLookupServiceMapper.INSTANCE.fromDTO(requestDto);

            // Step 2: Execute business logic on the domain object.
            List<String> cities = domainService.getCitiesForPostalCode(postalCode);

            // Step 3: Build the response DTO from the request DTO, replacing the city list.
            CityLookupServiceDTO responseDto = requestDto.toBuilder()
                    .clearCities()
                    .addAllCities(cities)
                    .build();

            GetCitiesForPostalCodeResponse response = GetCitiesForPostalCodeResponse.newBuilder()
                    .setDto(responseDto)
                    .build();

            // Step 4: Send the response and complete the RPC.
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to retrieve cities for postal code: " + postalCode)
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}