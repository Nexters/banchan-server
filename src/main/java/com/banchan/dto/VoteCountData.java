package com.banchan.dto;

import lombok.Data;

@Data
public class VoteCountData {

    long ansA, ansB, total;

    public VoteCountData(long ansA, long total) {
        this.ansA = ansA;
        this.ansB = total - ansA;
        this.total = total;
    }
}
