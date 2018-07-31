package com.banchan.vo;

import lombok.Value;

import java.util.Map;

@Value
public class RawQuestion {
    private int userId;
    private String type;
    private Map<String, String> details;
    private Map<String, byte[]> images;
}
