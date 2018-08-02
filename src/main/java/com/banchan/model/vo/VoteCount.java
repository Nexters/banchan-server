package com.banchan.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VoteCount {
    long ansA, ansB, total;
}
