package com.goodskill.monomorph.dto.generated.server;

import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.StringValue;
import com.goodskill.dto.Result;
import com.goodskill.monomorph.dto.generated.proto.result.ResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link Result} and {@link ResultDTO}.
 */
@Mapper(componentModel = "default")
public interface ResultMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

    /**
     * Maps from {@link ResultDTO} to {@link Result}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link Result} object.
     */
    Result fromDTO(ResultDTO dto);

    /**
     * Maps from {@link Result} to {@link ResultDTO}.
     *
     * @param domain The source domain object.
     * @return The mapped {@link ResultDTO} object.
     */
    ResultDTO toDTO(Result domain);

    /**
     * Converts a domain result payload into a protobuf {@link Any}.
     *
     * <p>Because {@code Result.data} is a generic {@code T} that erases to
     * {@code Object}, MapStruct cannot automatically map it to protobuf
     * {@link Any}. Common scalar types are wrapped in their protobuf well-known
     * wrapper types so they can be packed and later unpacked symmetrically.</p>
     *
     * @param value The domain payload.
     * @return The packed {@link Any}, or {@code null} when the value is null.
     */
    default Any map(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return Any.pack(StringValue.of((String) value));
        }
        if (value instanceof Long) {
            return Any.pack(Int64Value.of((Long) value));
        }
        if (value instanceof Integer) {
            return Any.pack(Int32Value.of((Integer) value));
        }
        if (value instanceof Boolean) {
            return Any.pack(BoolValue.of((Boolean) value));
        }
        if (value instanceof Double) {
            return Any.pack(DoubleValue.of((Double) value));
        }
        if (value instanceof Float) {
            return Any.pack(FloatValue.of((Float) value));
        }
        if (value instanceof byte[]) {
            return Any.pack(BytesValue.of(ByteString.copyFrom((byte[]) value)));
        }
        if (value instanceof Message) {
            return Any.pack((Message) value);
        }
        throw new IllegalArgumentException("Unsupported Result data type: " + value.getClass().getName());
    }

    /**
     * Converts a protobuf {@link Any} back into a domain result payload.
     *
     * @param value The packed {@link Any}.
     * @return The unpacked payload, or {@code null} when the value is null.
     */
    default Object map(Any value) {
        if (value == null) {
            return null;
        }
        try {
            if (value.is(StringValue.class)) {
                return value.unpack(StringValue.class).getValue();
            }
            if (value.is(Int64Value.class)) {
                return value.unpack(Int64Value.class).getValue();
            }
            if (value.is(Int32Value.class)) {
                return value.unpack(Int32Value.class).getValue();
            }
            if (value.is(BoolValue.class)) {
                return value.unpack(BoolValue.class).getValue();
            }
            if (value.is(DoubleValue.class)) {
                return value.unpack(DoubleValue.class).getValue();
            }
            if (value.is(FloatValue.class)) {
                return value.unpack(FloatValue.class).getValue();
            }
            if (value.is(BytesValue.class)) {
                return value.unpack(BytesValue.class).getValue().toByteArray();
            }
            throw new IllegalArgumentException("Unsupported protobuf Any type: " + value.getTypeUrl());
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalStateException("Failed to unpack protobuf Any data", e);
        }
    }
}

