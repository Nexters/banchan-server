package com.banchan.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "questions")
@Builder
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "random_order")
    private Integer randomOrder;

    @Column(name = "write_time")
    private LocalDateTime writeTime;
}