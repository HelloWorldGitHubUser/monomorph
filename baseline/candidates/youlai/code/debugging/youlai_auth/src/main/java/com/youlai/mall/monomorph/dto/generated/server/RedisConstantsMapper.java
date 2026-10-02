

package com.youlai.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.RedisConstantsDTO;
import com.youlai.mall.constant.RedisConstants;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link RedisConstants} and {@link RedisConstantsDTO}.
 */
@Mapper(componentModel = "default") 
public interface RedisConstantsMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    RedisConstantsMapper INSTANCE = Mappers.getMapper(RedisConstantsMapper.class);

    /**
     * Maps from {@link RedisConstantsDTO} to {@link RedisConstants}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link RedisConstants} object.
     */
    RedisConstants fromDTO(RedisConstantsDTO dto);

    /**
     * Maps from {@link RedisConstants} to {@link RedisConstantsDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link RedisConstantsDTO} object.
     */
    RedisConstantsDTO toDTO(RedisConstants domain);

}