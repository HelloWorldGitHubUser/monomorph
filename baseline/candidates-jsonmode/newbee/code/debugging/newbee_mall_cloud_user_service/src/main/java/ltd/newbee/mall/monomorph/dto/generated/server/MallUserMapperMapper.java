

package ltd.newbee.mall.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.MallUserMapperDTO;
import ltd.newbee.mall.dao.MallUserMapper;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link MallUserMapper} and {@link MallUserMapperDTO}.
 */
@Mapper(componentModel = "default") 
public interface MallUserMapperMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    MallUserMapperMapper INSTANCE = Mappers.getMapper(MallUserMapperMapper.class);

    /**
     * Maps from {@link MallUserMapperDTO} to {@link MallUserMapper}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link MallUserMapper} object.
     */
    MallUserMapper fromDTO(MallUserMapperDTO dto);

    /**
     * Maps from {@link MallUserMapper} to {@link MallUserMapperDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link MallUserMapperDTO} object.
     */
    MallUserMapperDTO toDTO(MallUserMapper domain);

}