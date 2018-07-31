package com.banchan.vo;

import lombok.Value;

@Value
public class RawVote {

    private int userId;
    private int questionId;
    private String answer;
}
