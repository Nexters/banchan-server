package com.banchan.dto;

import lombok.Data;

@Data
public class VoteCountRaw {

    long ansA, ansB, total;

    public VoteCountRaw(long ansA, long total) {
        this.ansA = ansA;
        this.ansB = total - ansA;
        this.total = total;
    }
}
