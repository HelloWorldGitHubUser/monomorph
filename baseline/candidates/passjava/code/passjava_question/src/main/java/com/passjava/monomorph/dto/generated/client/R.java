package com.passjava.monomorph.dto.generated.client;

import com.passjava.monomorph.dto.generated.proto.r.*;
import com.google.protobuf.NullValue;
import com.google.protobuf.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated DTO gRPC client for {@link R} and {@link RDTO}.
 * Uses composition to maintain the public API of the original R class
 * while storing all data in an RDTO instance.
 */
public class R {
    private RDTO dtoInstance;

    /**
     * Public no-arg constructor matching the original R() constructor.
     * Initializes the DTO with default code=0 and msg="success" entries.
     */
    public R() {
        this.dtoInstance = RDTO.newBuilder().build();
        put("code", 0);
        put("msg", "success");
    }

    /**
     * DTO constructor. Used by fromDTO/toDTO conversion.
     */
    public R(RDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null ? RDTO.newBuilder().build() : dtoInstance;
    }

    // --- Original static factory methods ---

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            r.put(entry.getKey(), entry.getValue());
        }
        return r;
    }

    public static R ok() {
        return new R();
    }

    /**
     * Overridden put method that returns this R instance.
     * Stores the key/value pair in the DTO's entrySet, keySet, and values fields.
     */
    public R put(String key, Object value) {
        Value protoValue = toProtoValue(value);

        List<RDTO.EntryDTO> entries = new ArrayList<>(dtoInstance.getEntrySetList());
        // Remove existing entry with the same key, if present
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getKey().equals(key)) {
                entries.remove(i);
                break;
            }
        }
        entries.add(RDTO.EntryDTO.newBuilder().setKey(key).setValue(protoValue).build());

        // Rebuild keySet and values to stay consistent with entrySet
        List<String> keys = new ArrayList<>();
        List<Value> values = new ArrayList<>();
        for (RDTO.EntryDTO entry : entries) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }

        dtoInstance = dtoInstance.toBuilder()
                .clearEntrySet()
                .addAllEntrySet(entries)
                .clearKeySet()
                .addAllKeySet(keys)
                .clearValues()
                .addAllValues(values)
                .build();

        return this;
    }

    private static Value toProtoValue(Object value) {
        if (value == null) {
            return Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();
        } else if (value instanceof String) {
            return Value.newBuilder().setStringValue((String) value).build();
        } else if (value instanceof Integer) {
            return Value.newBuilder().setNumberValue(((Integer) value).doubleValue()).build();
        } else if (value instanceof Long) {
            return Value.newBuilder().setNumberValue(((Long) value).doubleValue()).build();
        } else if (value instanceof Double) {
            return Value.newBuilder().setNumberValue((Double) value).build();
        } else if (value instanceof Float) {
            return Value.newBuilder().setNumberValue(((Float) value).doubleValue()).build();
        } else if (value instanceof Boolean) {
            return Value.newBuilder().setBoolValue((Boolean) value).build();
        } else {
            // Fallback for unsupported types: store as string representation
            return Value.newBuilder().setStringValue(value.toString()).build();
        }
    }

    // --- DTO mapping methods ---

    public RDTO toDTO() {
        return this.dtoInstance;
    }

    public static R fromDTO(RDTO dtoInstance) {
        return new R(dtoInstance);
    }

    // --- DTO getters and setters ---

    public int getDEFAULT_INITIAL_CAPACITY() {
        return dtoInstance.getDefaultInitialCapacity();
    }

    public void setDEFAULT_INITIAL_CAPACITY(int value) {
        dtoInstance = dtoInstance.toBuilder().setDefaultInitialCapacity(value).build();
    }

    public float getDEFAULT_LOAD_FACTOR() {
        return dtoInstance.getDefaultLoadFactor();
    }

    public void setDEFAULT_LOAD_FACTOR(float value) {
        dtoInstance = dtoInstance.toBuilder().setDefaultLoadFactor(value).build();
    }

    public int getMAXIMUM_CAPACITY() {
        return dtoInstance.getMaximumCapacity();
    }

    public void setMAXIMUM_CAPACITY(int value) {
        dtoInstance = dtoInstance.toBuilder().setMaximumCapacity(value).build();
    }

    public int getMIN_TREEIFY_CAPACITY() {
        return dtoInstance.getMinTreeifyCapacity();
    }

    public void setMIN_TREEIFY_CAPACITY(int value) {
        dtoInstance = dtoInstance.toBuilder().setMinTreeifyCapacity(value).build();
    }

    public int getTREEIFY_THRESHOLD() {
        return dtoInstance.getTreeifyThreshold();
    }

    public void setTREEIFY_THRESHOLD(int value) {
        dtoInstance = dtoInstance.toBuilder().setTreeifyThreshold(value).build();
    }

    public int getUNTREEIFY_THRESHOLD() {
        return dtoInstance.getUntreeifyThreshold();
    }

    public void setUNTREEIFY_THRESHOLD(int value) {
        dtoInstance = dtoInstance.toBuilder().setUntreeifyThreshold(value).build();
    }

    public List<RDTO.EntryDTO> getEntrySet() {
        return dtoInstance.getEntrySetList();
    }

    public void setEntrySet(List<RDTO.EntryDTO> entrySet) {
        dtoInstance = dtoInstance.toBuilder().clearEntrySet().addAllEntrySet(entrySet).build();
    }

    public List<String> getKeySet() {
        return dtoInstance.getKeySetList();
    }

    public void setKeySet(List<String> keySet) {
        dtoInstance = dtoInstance.toBuilder().clearKeySet().addAllKeySet(keySet).build();
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

    public List<RDTO.NodeDTO> getTable() {
        return dtoInstance.getTableList();
    }

    public void setTable(List<RDTO.NodeDTO> table) {
        dtoInstance = dtoInstance.toBuilder().clearTable().addAllTable(table).build();
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
        dtoInstance = dtoInstance.toBuilder().clearValues().addAllValues(values).build();
    }
}

