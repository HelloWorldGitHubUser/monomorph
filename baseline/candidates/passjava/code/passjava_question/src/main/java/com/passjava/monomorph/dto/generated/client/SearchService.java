package com.passjava.monomorph.dto.generated.client;

// gRPC imports
import com.passjava.monomorph.dto.generated.proto.searchservice.*;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import com.passjava.dto.QuestionEsModel;
import com.passjava.monomorph.dto.generated.server.QuestionEsModelMapper;
import com.passjava.monomorph.dto.generated.client.R;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.passjava.monomorph.id.generated.helpers.ServiceRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Auto-generated DTO gRPC client
 * {@link SearchService} and {@link SearchServiceDTO}.
 */
public class SearchService {
    private SearchServiceDTO dtoInstance;

    // Constructor accepting a DTO instance
    public SearchService(SearchServiceDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // No-argument constructor for creating an empty DTO
    public SearchService() {
        this.dtoInstance = SearchServiceDTO.newBuilder().build();
    }

    // mapping methods
    public SearchServiceDTO toDTO() {
        return this.dtoInstance;
    }

    public static SearchService fromDTO(SearchServiceDTO dtoInstance) {
        return new SearchService(dtoInstance);
    }

    private static final String TARGET_SERVICE_ID = "passjava_search";

    private ManagedChannel businessChannel;
    private SearchServiceServiceGrpc.SearchServiceServiceBlockingStub businessStub;

    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = SearchServiceServiceGrpc.newBlockingStub(businessChannel);
    }

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

    // --- START OF gRPC METHOD IMPLEMENTATIONS ---
    public R saveQuestion(QuestionEsModel questionEsModel) {
        try {
            performRpcSetup();
            SaveQuestionRequest request = SaveQuestionRequest.newBuilder()
                    .setDto(this.dtoInstance)
                    .setQuestionEsModel(QuestionEsModelMapper.INSTANCE.toDTO(questionEsModel))
                    .build();
            SaveQuestionResponse response = businessStub.saveQuestion(request);
            RDTO resultDTO = response.getResult();
            return R.fromDTO(resultDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call saveQuestion via gRPC", e);
        } finally {
            performSubclassRpcCleanup();
        }
    }
    // --- END OF gRPC METHOD IMPLEMENTATIONS ---

    // --- START OF DTO GETTERS AND SETTERS ---
    public String getAnswer() {
        return dtoInstance.getAnswer();
    }

    public void setAnswer(String answer) {
        this.dtoInstance = this.dtoInstance.toBuilder().setAnswer(answer).build();
    }

    public long getId() {
        return dtoInstance.getId();
    }

    public void setId(long id) {
        this.dtoInstance = this.dtoInstance.toBuilder().setId(id).build();
    }

    public String getTitle() {
        return dtoInstance.getTitle();
    }

    public void setTitle(String title) {
        this.dtoInstance = this.dtoInstance.toBuilder().setTitle(title).build();
    }

    public String getTypeName() {
        return dtoInstance.getTypeName();
    }

    public void setTypeName(String typeName) {
        this.dtoInstance = this.dtoInstance.toBuilder().setTypeName(typeName).build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}

