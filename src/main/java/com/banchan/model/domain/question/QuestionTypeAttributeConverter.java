package com.banchan.model.domain.question;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class QuestionTypeAttributeConverter implements AttributeConverter<QuestionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(QuestionType attribute) {
        return attribute.getValue();
    }

    @Override
    public QuestionType convertToEntityAttribute(Integer dbData) {
        return QuestionType.valueOf(dbData);
    }
}
