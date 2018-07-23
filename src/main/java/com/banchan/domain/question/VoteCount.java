package com.banchan.domain.question;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VoteCount {
    int total, ansA, ansB;

}
