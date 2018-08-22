package com.banchan.model.domain.question;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AnswerTypeAttributeConverter implements AttributeConverter<AnswerType, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(AnswerType attribute) {
        if(attribute == null) return null;
        return attribute.getValue();
    }

    @Override
    public AnswerType convertToEntityAttribute(Boolean dbData) {
        return AnswerType.valueOf(dbData);
    }
}
