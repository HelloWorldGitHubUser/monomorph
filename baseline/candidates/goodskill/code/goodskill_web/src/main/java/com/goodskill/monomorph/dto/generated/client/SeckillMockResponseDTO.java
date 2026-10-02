package com.goodskill.monomorph.dto.generated.client;

import com.goodskill.monomorph.dto.generated.proto.seckillmockresponsedto.SeckillMockResponseDTODTO;

/**
 * Auto-generated DTO gRPC client
 * {@link SeckillMockResponseDTO} and {@link SeckillMockResponseDTODTO}.
 *
 * <p>This class serves as a client-side DTO that wraps the protobuf message
 * {@link SeckillMockResponseDTODTO} using composition. It exposes the same
 * getters/setters as the original {@code SeckillMockResponseDTO} class, while
 * all data is stored in the protobuf DTO instance.</p>
 */
public class SeckillMockResponseDTO {

    private SeckillMockResponseDTODTO dtoInstance;

    /**
     * No-args constructor, equivalent to the original Lombok {@code @NoArgsConstructor}.
     */
    public SeckillMockResponseDTO() {
        this(SeckillMockResponseDTODTO.newBuilder()
                .setSerialVersionUID(1L)
                .build());
    }

    /**
     * All-args constructor, equivalent to the original Lombok {@code @AllArgsConstructor}.
     */
    public SeckillMockResponseDTO(long seckillId, String note, Boolean status, String taskId) {
        this(SeckillMockResponseDTODTO.newBuilder()
                .setSerialVersionUID(1L)
                .setSeckillId(seckillId)
                .setNote(note == null ? "" : note)
                .setStatus(status != null && status)
                .setTaskId(taskId == null ? "" : taskId)
                .build());
    }

    /**
     * Constructor that accepts a protobuf DTO instance.
     * Kept public to match the provided template and enable external DTO wrapping.
     */
    public SeckillMockResponseDTO(SeckillMockResponseDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Maps this client wrapper to its underlying protobuf DTO.
     *
     * @return the current {@link SeckillMockResponseDTODTO} instance
     */
    public SeckillMockResponseDTODTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client wrapper from a protobuf DTO instance.
     *
     * @param dtoInstance the protobuf DTO to wrap
     * @return a new {@link SeckillMockResponseDTO} instance
     */
    public static SeckillMockResponseDTO fromDTO(SeckillMockResponseDTODTO dtoInstance) {
        return new SeckillMockResponseDTO(dtoInstance);
    }

    // --- DTO field getters and setters ---

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUID();
    }

    public void setSerialVersionUID(long serialVersionUID) {
        dtoInstance = dtoInstance.toBuilder()
                .setSerialVersionUID(serialVersionUID)
                .build();
    }

    public long getSeckillId() {
        return dtoInstance.getSeckillId();
    }

    public void setSeckillId(long seckillId) {
        dtoInstance = dtoInstance.toBuilder()
                .setSeckillId(seckillId)
                .build();
    }

    public String getNote() {
        return dtoInstance.getNote();
    }

    public void setNote(String note) {
        dtoInstance = dtoInstance.toBuilder()
                .setNote(note == null ? "" : note)
                .build();
    }

    /**
     * Returns the status as a {@link Boolean} to maintain API compatibility with the
     * original class. Proto3 {@code bool} cannot represent {@code null}, so a
     * protobuf default value of {@code false} is returned as {@code Boolean.FALSE}.
     */
    public Boolean getStatus() {
        return dtoInstance.getStatus();
    }

    /**
     * Sets the status. A {@code null} value is coerced to {@code false} because
     * protobuf {@code bool} is a primitive type and cannot store {@code null}.
     */
    public void setStatus(Boolean status) {
        dtoInstance = dtoInstance.toBuilder()
                .setStatus(status != null && status)
                .build();
    }

    public String getTaskId() {
        return dtoInstance.getTaskId();
    }

    public void setTaskId(String taskId) {
        dtoInstance = dtoInstance.toBuilder()
                .setTaskId(taskId == null ? "" : taskId)
                .build();
    }
}
