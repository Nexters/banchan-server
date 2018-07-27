package com.banchan.vo;

import lombok.Value;

import java.util.Map;

@Value
public class RawQuestion {
    private int userId;
    private Map<String, Object> details;
}
