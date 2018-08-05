package com.banchan.model.domain.question;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DetailTypeAttributeConverter implements AttributeConverter<DetailType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DetailType attribute) {
        return attribute.getValue();
    }

    @Override
    public DetailType convertToEntityAttribute(Integer dbData) {
        return DetailType.valueOf(dbData);
    }
}
