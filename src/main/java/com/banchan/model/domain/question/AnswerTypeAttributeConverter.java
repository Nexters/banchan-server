package com.banchan.model.domain.question;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter
public class AnswerTypeAttributeConverter implements AttributeConverter<AnswerType, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(AnswerType attribute) {
        return Optional.ofNullable(attribute).orElse(AnswerType.UNDECIDED).getValue();
    }

    @Override
    public AnswerType convertToEntityAttribute(Boolean dbData) {
        return AnswerType.valueOf(dbData);
    }
}
