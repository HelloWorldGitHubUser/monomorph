package com.passjava.monomorph.dto.generated.client;

import com.google.protobuf.Value;
import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import java.util.List;

/**
 * Auto-generated DTO gRPC client
 * {@link R} and {@link RDTO}.
 */
public class R {
    private RDTO dtoInstance;

    public R() {
        this(RDTO.newBuilder().build());
    }

    public R(RDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // mapping methods
    public RDTO toDTO() {
        return this.dtoInstance;
    }

    public static R fromDTO(RDTO dtoInstance) {
        return new R(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No service methods are defined in the generated proto.

    // --- START OF DTO GETTERS AND SETTERS ---
    public int getDefaultInitialCapacity() {
        return dtoInstance.getDefaultInitialCapacity();
    }

    public void setDefaultInitialCapacity(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setDefaultInitialCapacity(value)
                .build();
    }

    public float getDefaultLoadFactor() {
        return dtoInstance.getDefaultLoadFactor();
    }

    public void setDefaultLoadFactor(float value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setDefaultLoadFactor(value)
                .build();
    }

    public int getMaximumCapacity() {
        return dtoInstance.getMaximumCapacity();
    }

    public void setMaximumCapacity(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setMaximumCapacity(value)
                .build();
    }

    public int getMinTreeifyCapacity() {
        return dtoInstance.getMinTreeifyCapacity();
    }

    public void setMinTreeifyCapacity(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setMinTreeifyCapacity(value)
                .build();
    }

    public int getTreeifyThreshold() {
        return dtoInstance.getTreeifyThreshold();
    }

    public void setTreeifyThreshold(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setTreeifyThreshold(value)
                .build();
    }

    public int getUntreeifyThreshold() {
        return dtoInstance.getUntreeifyThreshold();
    }

    public void setUntreeifyThreshold(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setUntreeifyThreshold(value)
                .build();
    }

    public List<RDTO.EntryDTO> getEntrySetList() {
        return dtoInstance.getEntrySetList();
    }

    public void setEntrySet(List<RDTO.EntryDTO> entrySet) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .clearEntrySet()
                .addAllEntrySet(entrySet)
                .build();
    }

    public List<String> getKeySetList() {
        return dtoInstance.getKeySetList();
    }

    public void setKeySet(List<String> keySet) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .clearKeySet()
                .addAllKeySet(keySet)
                .build();
    }

    public float getLoadFactor() {
        return dtoInstance.getLoadFactor();
    }

    public void setLoadFactor(float value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setLoadFactor(value)
                .build();
    }

    public int getModCount() {
        return dtoInstance.getModCount();
    }

    public void setModCount(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setModCount(value)
                .build();
    }

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUID();
    }

    public void setSerialVersionUID(long value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setSerialVersionUID(value)
                .build();
    }

    public int getSize() {
        return dtoInstance.getSize();
    }

    public void setSize(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setSize(value)
                .build();
    }

    public List<RDTO.NodeDTO> getTableList() {
        return dtoInstance.getTableList();
    }

    public void setTable(List<RDTO.NodeDTO> table) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .clearTable()
                .addAllTable(table)
                .build();
    }

    public int getThreshold() {
        return dtoInstance.getThreshold();
    }

    public void setThreshold(int value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setThreshold(value)
                .build();
    }

    public List<Value> getValuesList() {
        return dtoInstance.getValuesList();
    }

    public void setValues(List<Value> values) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .clearValues()
                .addAllValues(values)
                .build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}