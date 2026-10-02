

package com.goodskill.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.goodskill.monomorph.dto.generated.proto.seckillmockresponselistener.SeckillMockResponseListenerDTO;
import com.goodskill.listener.SeckillMockResponseListener;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link SeckillMockResponseListener} and {@link SeckillMockResponseListenerDTO}.
 */
@Mapper(componentModel = "default") 
public interface SeckillMockResponseListenerMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    SeckillMockResponseListenerMapper INSTANCE = Mappers.getMapper(SeckillMockResponseListenerMapper.class);

    /**
     * Maps from {@link SeckillMockResponseListenerDTO} to {@link SeckillMockResponseListener}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link SeckillMockResponseListener} object.
     */
    SeckillMockResponseListener fromDTO(SeckillMockResponseListenerDTO dto);

    /**
     * Maps from {@link SeckillMockResponseListener} to {@link SeckillMockResponseListenerDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link SeckillMockResponseListenerDTO} object.
     */
    SeckillMockResponseListenerDTO toDTO(SeckillMockResponseListener domain);

}