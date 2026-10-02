package com.goodskill.monomorph.dto.generated.client;

// gRPC imports
import com.goodskill.monomorph.dto.generated.proto.result.*;
import com.google.protobuf.Any;

/**
 * Auto-generated DTO gRPC client
 * {@link Result} and {@link ResultDTO}.
 */
public class Result {

    public static final int SUCCESS = 0;
    public static final int FAIL = 500;

    private ResultDTO dtoInstance;

    public Result(ResultDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public Result() {
        this(ResultDTO.newBuilder().build());
    }

    public ResultDTO toDTO() {
        return this.dtoInstance;
    }

    public static Result fromDTO(ResultDTO dtoInstance) {
        return new Result(dtoInstance);
    }

    // Static factory methods matching original API
    public static Result ok() {
        return restResult(null, SUCCESS, null);
    }

    public static Result ok(Any data) {
        return restResult(data, SUCCESS, null);
    }

    public static Result ok(Any data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static Result fail() {
        return restResult(null, FAIL, null);
    }

    public static Result fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static Result fail(Any data) {
        return restResult(data, FAIL, null);
    }

    public static Result fail(Any data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static Result fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static Result restResult(Any data, int code, String msg) {
        ResultDTO.Builder builder = ResultDTO.newBuilder()
                .setCode(code);
        if (data != null) {
            builder.setData(data);
        }
        if (msg != null) {
            builder.setMsg(msg);
        }
        return new Result(builder.build());
    }

    // --- START OF DTO GETTERS AND SETTERS ---
    public int getCode() {
        return dtoInstance.getCode();
    }

    public void setCode(int code) {
        dtoInstance = dtoInstance.toBuilder().setCode(code).build();
    }

    public String getMsg() {
        return dtoInstance.getMsg();
    }

    public void setMsg(String msg) {
        dtoInstance = dtoInstance.toBuilder().setMsg(msg).build();
    }

    public Any getData() {
        return dtoInstance.getData();
    }

    public void setData(Any data) {
        dtoInstance = dtoInstance.toBuilder().setData(data).build();
    }

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUID();
    }

    public void setSerialVersionUID(long serialVersionUID) {
        dtoInstance = dtoInstance.toBuilder().setSerialVersionUID(serialVersionUID).build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}