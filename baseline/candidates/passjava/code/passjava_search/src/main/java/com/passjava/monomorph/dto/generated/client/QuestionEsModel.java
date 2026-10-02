package com.passjava.monomorph.dto.generated.client;

// gRPC imports
import com.passjava.monomorph.dto.generated.proto.questionesmodel.QuestionEsModelDTO;

/**
 * Auto-generated DTO gRPC client
 * {@link QuestionEsModel} and {@link QuestionEsModelDTO}.
 */
public class QuestionEsModel {
    private QuestionEsModelDTO dtoInstance;

    /**
     * No-args constructor matching the original Lombok-generated API.
     */
    public QuestionEsModel() {
        this(QuestionEsModelDTO.newBuilder().build());
    }

    /**
     * Private DTO-based constructor used by {@link #fromDTO(QuestionEsModelDTO)}.
     */
    private QuestionEsModel(QuestionEsModelDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Maps this client instance to its underlying DTO.
     */
    public QuestionEsModelDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from a DTO.
     */
    public static QuestionEsModel fromDTO(QuestionEsModelDTO dtoInstance) {
        return new QuestionEsModel(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        dtoInstance = dtoInstance.toBuilder().setId(id).build();
    }

    public String getTitle() {
        return dtoInstance.getTitle();
    }

    public void setTitle(String title) {
        dtoInstance = dtoInstance.toBuilder().setTitle(title).build();
    }

    public String getAnswer() {
        return dtoInstance.getAnswer();
    }

    public void setAnswer(String answer) {
        dtoInstance = dtoInstance.toBuilder().setAnswer(answer).build();
    }

    public String getTypeName() {
        return dtoInstance.getTypeName();
    }

    public void setTypeName(String typeName) {
        dtoInstance = dtoInstance.toBuilder().setTypeName(typeName).build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}
