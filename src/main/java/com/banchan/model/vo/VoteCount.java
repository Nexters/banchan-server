package com.banchan.model.vo;

import lombok.Data;

@Data
public class VoteCount {
    private final long a, b, total;

    public VoteCount(long a, long b){
        this.a = a;
        this.b = b;
        this.total = a + b;
    }
}
