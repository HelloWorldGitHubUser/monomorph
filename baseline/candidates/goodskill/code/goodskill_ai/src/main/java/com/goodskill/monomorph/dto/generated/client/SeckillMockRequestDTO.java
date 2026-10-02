package com.goodskill.monomorph.dto.generated.client;

import com.goodskill.monomorph.dto.generated.proto.seckillmockrequestdto.*;

public class SeckillMockRequestDTO {
    private SeckillMockRequestDTODTO dtoInstance;

    public SeckillMockRequestDTO() {
        this.dtoInstance = SeckillMockRequestDTODTO.getDefaultInstance();
    }

    public SeckillMockRequestDTO(long seckillId, int count, String phoneNumber) {
        this.dtoInstance = SeckillMockRequestDTODTO.newBuilder()
                .setSeckillId(seckillId)
                .setCount(count)
                .setPhoneNumber(phoneNumber)
                .build();
    }

    public SeckillMockRequestDTO(long seckillId, int count, String phoneNumber, String taskId) {
        this.dtoInstance = SeckillMockRequestDTODTO.newBuilder()
                .setSeckillId(seckillId)
                .setCount(count)
                .setPhoneNumber(phoneNumber)
                .setTaskId(taskId)
                .build();
    }

    public SeckillMockRequestDTO(long seckillId, int count, String phoneNumber,
                                 String requestTime, String taskId) {
        this.dtoInstance = SeckillMockRequestDTODTO.newBuilder()
                .setSeckillId(seckillId)
                .setCount(count)
                .setPhoneNumber(phoneNumber)
                .setRequestTime(requestTime)
                .setTaskId(taskId)
                .build();
    }

    public SeckillMockRequestDTO(SeckillMockRequestDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public SeckillMockRequestDTODTO toDTO() {
        return dtoInstance;
    }

    public static SeckillMockRequestDTO fromDTO(SeckillMockRequestDTODTO dtoInstance) {
        return new SeckillMockRequestDTO(dtoInstance);
    }

    public long getSeckillId() {
        return dtoInstance.getSeckillId();
    }

    public void setSeckillId(long seckillId) {
        dtoInstance = dtoInstance.toBuilder().setSeckillId(seckillId).build();
    }

    public int getCount() {
        return dtoInstance.getCount();
    }

    public void setCount(int count) {
        dtoInstance = dtoInstance.toBuilder().setCount(count).build();
    }

    public String getPhoneNumber() {
        return dtoInstance.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        dtoInstance = dtoInstance.toBuilder().setPhoneNumber(phoneNumber).build();
    }

    public String getRequestTime() {
        return dtoInstance.getRequestTime();
    }

    public void setRequestTime(String requestTime) {
        dtoInstance = dtoInstance.toBuilder().setRequestTime(requestTime).build();
    }

    public String getTaskId() {
        return dtoInstance.getTaskId();
    }

    public void setTaskId(String taskId) {
        dtoInstance = dtoInstance.toBuilder().setTaskId(taskId).build();
    }
}