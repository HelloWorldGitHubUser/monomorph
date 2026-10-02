package com.goodskill.monomorph.dto.generated.client;

import com.goodskill.monomorph.dto.generated.proto.seckillmockrequestdto.SeckillMockRequestDTODTO;

public class SeckillMockRequestDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private SeckillMockRequestDTODTO dtoInstance;

    private SeckillMockRequestDTO(SeckillMockRequestDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public SeckillMockRequestDTO() {
        this(SeckillMockRequestDTODTO.newBuilder().build());
    }

    public SeckillMockRequestDTO(long seckillId, int count, String phoneNumber) {
        this(SeckillMockRequestDTODTO.newBuilder()
                .setSeckillId(seckillId)
                .setCount(count)
                .setPhoneNumber(phoneNumber)
                .build());
    }

    public SeckillMockRequestDTO(long seckillId, int count, String phoneNumber, String taskId) {
        this(SeckillMockRequestDTODTO.newBuilder()
                .setSeckillId(seckillId)
                .setCount(count)
                .setPhoneNumber(phoneNumber)
                .setTaskId(taskId)
                .build());
    }

    public SeckillMockRequestDTO(long seckillId, int count, String phoneNumber, String requestTime, String taskId) {
        this(SeckillMockRequestDTODTO.newBuilder()
                .setSeckillId(seckillId)
                .setCount(count)
                .setPhoneNumber(phoneNumber)
                .setRequestTime(requestTime)
                .setTaskId(taskId)
                .build());
    }

    public SeckillMockRequestDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static SeckillMockRequestDTO fromDTO(SeckillMockRequestDTODTO dtoInstance) {
        return new SeckillMockRequestDTO(dtoInstance);
    }

    public long getSeckillId() {
        return dtoInstance.getSeckillId();
    }

    public void setSeckillId(long seckillId) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setSeckillId(seckillId)
                .build();
    }

    public int getCount() {
        return dtoInstance.getCount();
    }

    public void setCount(int count) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setCount(count)
                .build();
    }

    public String getPhoneNumber() {
        return dtoInstance.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setPhoneNumber(phoneNumber)
                .build();
    }

    public String getRequestTime() {
        return dtoInstance.getRequestTime();
    }

    public void setRequestTime(String requestTime) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setRequestTime(requestTime)
                .build();
    }

    public String getTaskId() {
        return dtoInstance.getTaskId();
    }

    public void setTaskId(String taskId) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setTaskId(taskId)
                .build();
    }
}