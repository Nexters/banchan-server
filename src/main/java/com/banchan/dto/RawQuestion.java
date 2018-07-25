package com.banchan.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RawQuestion {

    private int userId;
    private Map<String, String> details;
}
