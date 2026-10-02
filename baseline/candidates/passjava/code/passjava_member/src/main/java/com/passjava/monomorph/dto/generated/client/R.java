package com.passjava.monomorph.dto.generated.client;

import com.passjava.monomorph.dto.generated.proto.r.RDTO;
import com.passjava.monomorph.dto.generated.proto.r.RDTO.EntryDTO;
import com.passjava.monomorph.dto.generated.proto.r.RDTO.NodeDTO;
import com.google.protobuf.Value;
import java.util.List;

/**
 * Auto-generated DTO gRPC client for {@link R} and {@link RDTO}.
 * Uses composition to expose the same API as the original class
 * through DTO getters and setters.
 */
public class R {
    private RDTO dtoInstance;

    /**
     * Default constructor – mirrors the original no-arg constructor.
     * Initializes an empty RDTO instance.
     */
    public R() {
        this.dtoInstance = RDTO.newBuilder().build();
    }

    /**
     * DTO constructor – initializes from an existing RDTO instance.
     */
    public R(RDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // mapping methods
    public RDTO toDTO() {
        return this.dtoInstance;
    }

    public static R fromDTO(RDTO dtoInstance) {
        R instance = new R(dtoInstance);
        return instance;
    }

    // implementation of the gRPC exposed methods
    // (none – the proto definition contains only a DTO message, no service)

    // --- START OF DTO GETTERS AND SETTERS ---

    public int getDEFAULTINITIALCAPACITY() {
        return dtoInstance.getDefaultInitialCapacity();
    }

    public void setDEFAULTINITIALCAPACITY(int value) {
        dtoInstance = dtoInstance.toBuilder().setDefaultInitialCapacity(value).build();
    }

    public float getDEFAULTLOADFACTOR() {
        return dtoInstance.getDefaultLoadFactor();
    }

    public void setDEFAULTLOADFACTOR(float value) {
        dtoInstance = dtoInstance.toBuilder().setDefaultLoadFactor(value).build();
    }

    public int getMAXIMUMCAPACITY() {
        return dtoInstance.getMaximumCapacity();
    }

    public void setMAXIMUMCAPACITY(int value) {
        dtoInstance = dtoInstance.toBuilder().setMaximumCapacity(value).build();
    }

    public int getMINTREEIFYCAPACITY() {
        return dtoInstance.getMinTreeifyCapacity();
    }

    public void setMINTREEIFYCAPACITY(int value) {
        dtoInstance = dtoInstance.toBuilder().setMinTreeifyCapacity(value).build();
    }

    public int getTREEIFYTHRESHOLD() {
        return dtoInstance.getTreeifyThreshold();
    }

    public void setTREEIFYTHRESHOLD(int value) {
        dtoInstance = dtoInstance.toBuilder().setTreeifyThreshold(value).build();
    }

    public int getUNTREEIFYTHRESHOLD() {
        return dtoInstance.getUntreeifyThreshold();
    }

    public void setUNTREEIFYTHRESHOLD(int value) {
        dtoInstance = dtoInstance.toBuilder().setUntreeifyThreshold(value).build();
    }

    public List<EntryDTO> getEntrySet() {
        return dtoInstance.getEntrySetList();
    }

    public void setEntrySet(List<EntryDTO> entrySet) {
        RDTO.Builder builder = dtoInstance.toBuilder();
        builder.clearEntrySet();
        builder.addAllEntrySet(entrySet);
        dtoInstance = builder.build();
    }

    public List<String> getKeySet() {
        return dtoInstance.getKeySetList();
    }

    public void setKeySet(List<String> keySet) {
        RDTO.Builder builder = dtoInstance.toBuilder();
        builder.clearKeySet();
        builder.addAllKeySet(keySet);
        dtoInstance = builder.build();
    }

    public float getLoadFactor() {
        return dtoInstance.getLoadFactor();
    }

    public void setLoadFactor(float value) {
        dtoInstance = dtoInstance.toBuilder().setLoadFactor(value).build();
    }

    public int getModCount() {
        return dtoInstance.getModCount();
    }

    public void setModCount(int value) {
        dtoInstance = dtoInstance.toBuilder().setModCount(value).build();
    }

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUid();
    }

    public void setSerialVersionUID(long value) {
        dtoInstance = dtoInstance.toBuilder().setSerialVersionUid(value).build();
    }

    public int getSize() {
        return dtoInstance.getSize();
    }

    public void setSize(int value) {
        dtoInstance = dtoInstance.toBuilder().setSize(value).build();
    }

    public List<NodeDTO> getTable() {
        return dtoInstance.getTableList();
    }

    public void setTable(List<NodeDTO> table) {
        RDTO.Builder builder = dtoInstance.toBuilder();
        builder.clearTable();
        builder.addAllTable(table);
        dtoInstance = builder.build();
    }

    public int getThreshold() {
        return dtoInstance.getThreshold();
    }

    public void setThreshold(int value) {
        dtoInstance = dtoInstance.toBuilder().setThreshold(value).build();
    }

    public List<Value> getValues() {
        return dtoInstance.getValuesList();
    }

    public void setValues(List<Value> values) {
        RDTO.Builder builder = dtoInstance.toBuilder();
        builder.clearValues();
        builder.addAllValues(values);
        dtoInstance = builder.build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}

