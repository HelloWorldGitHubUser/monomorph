package com.goodskill.monomorph.dto.generated.client;

import com.goodskill.monomorph.dto.generated.proto.seckillwebmockrequestdto.SeckillWebMockRequestDTODTO;

/**
 * Auto-generated DTO gRPC client for {@link SeckillWebMockRequestDTODTO}.
 * Provides the same getter/setter API as the original DTO class.
 */
public class SeckillWebMockRequestDTO {

    private SeckillWebMockRequestDTODTO dtoInstance;

    /**
     * No-args constructor compatible with the original Lombok {@code @Data} class.
     */
    public SeckillWebMockRequestDTO() {
        this.dtoInstance = SeckillWebMockRequestDTODTO.newBuilder().build();
    }

    private SeckillWebMockRequestDTO(SeckillWebMockRequestDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public SeckillWebMockRequestDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static SeckillWebMockRequestDTO fromDTO(SeckillWebMockRequestDTODTO dtoInstance) {
        return new SeckillWebMockRequestDTO(dtoInstance);
    }

    public Long getSeckillId() {
        return dtoInstance.getSeckillId();
    }

    public void setSeckillId(Long seckillId) {
        dtoInstance.setSeckillId(seckillId);
    }

    public int getSeckillCount() {
        return dtoInstance.getSeckillCount();
    }

    public void setSeckillCount(int seckillCount) {
        dtoInstance.setSeckillCount(seckillCount);
    }

    public int getRequestCount() {
        return dtoInstance.getRequestCount();
    }

    public void setRequestCount(int requestCount) {
        dtoInstance.setRequestCount(requestCount);
    }

    public String getTaskId() {
        return dtoInstance.getTaskId();
    }

    public void setTaskId(String taskId) {
        dtoInstance.setTaskId(taskId);
    }

    public Integer getCorePoolSize() {
        return dtoInstance.getCorePoolSize();
    }

    public void setCorePoolSize(Integer corePoolSize) {
        dtoInstance.setCorePoolSize(corePoolSize);
    }

    public Integer getMaxPoolSize() {
        return dtoInstance.getMaxPoolSize();
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        dtoInstance.setMaxPoolSize(maxPoolSize);
    }

    public boolean isAllowVirtualThread() {
        return dtoInstance.getAllowVirtualThread();
    }

    public void setAllowVirtualThread(boolean allowVirtualThread) {
        dtoInstance.setAllowVirtualThread(allowVirtualThread);
    }
}