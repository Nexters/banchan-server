package com.banchan.model.domain.question;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RewardTypeAttributeConverter implements AttributeConverter<RewardType, String> {

    @Override
    public String convertToDatabaseColumn(RewardType attribute) {
        return attribute.getValue();
    }

    @Override
    public RewardType convertToEntityAttribute(String dbData) {
        return RewardType.valueOfString(dbData);
    }
}
