package com.passjava.monomorph.dto.generated.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import com.passjava.monomorph.dto.generated.proto.searchservice.*;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import com.passjava.monomorph.dto.generated.proto.questionesmodel.QuestionEsModelDTO;
import com.passjava.monomorph.dto.generated.client.R;
import com.passjava.monomorph.dto.generated.client.QuestionEsModel;
import com.passjava.service.SearchService;

/**
 * gRPC Service implementation for SearchService.
 * - Handles gRPC requests for SearchService API.
 * - Interacts with Mapper for switching between DTO and SearchService instances.
 */
public class SearchServiceImpl extends SearchServiceServiceGrpc.SearchServiceServiceImplBase {

    @Override
    public void saveQuestion(SaveQuestionRequest request, StreamObserver<SaveQuestionResponse> responseObserver) {
        if (request == null || !request.hasDto() || !request.hasQuestionEsModel()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Both dto and questionEsModel are required")
                    .asRuntimeException());
            return;
        }

        try {
            SearchServiceDTO dto = request.getDto();
            SearchService original = SearchServiceMapper.INSTANCE.fromDTO(dto);
            if (original == null) {
                throw new IllegalStateException("Could not map SearchServiceDTO to SearchService");
            }

            QuestionEsModelDTO questionEsModelDto = request.getQuestionEsModel();
            QuestionEsModel questionEsModel = QuestionEsModel.fromDTO(questionEsModelDto);
            if (questionEsModel == null) {
                throw new IllegalStateException("Could not map QuestionEsModelDTO to QuestionEsModel");
            }

            R result = original.saveQuestion(questionEsModel);
            if (result == null) {
                throw new IllegalStateException("saveQuestion returned null");
            }

            RDTO resultDto = result.toDTO();
            if (resultDto == null) {
                throw new IllegalStateException("Could not convert R to RDTO");
            }

            SaveQuestionResponse response = SaveQuestionResponse.newBuilder()
                    .setDto(dto)
                    .setResult(resultDto)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to save question: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}