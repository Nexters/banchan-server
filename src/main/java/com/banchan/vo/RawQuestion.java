package com.banchan.vo;

import com.banchan.domain.question.QuestionType;
import lombok.Value;

import java.util.Map;

@Value
public class RawQuestion {
    private int userId;
    private QuestionType questionType;
    private Map<String, String> details;
    private Map<String, byte[]> images;
}
