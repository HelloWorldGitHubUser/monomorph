

package com.passjava.monomorph.dto.generated.server;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.passjava.monomorph.dto.generated.proto.questionesmodel.QuestionEsModelDTO;
import com.passjava.dto.QuestionEsModel;

/**
 * Auto-generated MapStruct mapper for converting between
 * {@link QuestionEsModel} and {@link QuestionEsModelDTO}.
 */
@Mapper(componentModel = "default") 
public interface QuestionEsModelMapper {
    /**
     * Singleton instance of this mapper. Use this to access mapping methods.
     */
    QuestionEsModelMapper INSTANCE = Mappers.getMapper(QuestionEsModelMapper.class);

    /**
     * Maps from {@link QuestionEsModelDTO} to {@link QuestionEsModel}.
     *
     * @param dto The source DTO object.
     * @return The mapped {@link QuestionEsModel} object.
     */
    QuestionEsModel fromDTO(QuestionEsModelDTO dto);

    /**
     * Maps from {@link QuestionEsModel} to {@link QuestionEsModelDTO}.
     *
     * @param original The source of the Original object.
     * @return The mapped {@link QuestionEsModelDTO} object.
     */
    QuestionEsModelDTO toDTO(QuestionEsModel domain);

}