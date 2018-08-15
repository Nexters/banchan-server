package com.banchan.model.entity;

import com.banchan.model.domain.question.AnswerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "questions")
@Builder
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "random_order")
    private Integer randomOrder;

    @Column(name = "decision")
    private AnswerType decision;

    @JsonIgnore
    @Column(name = "write_time")
    private LocalDateTime writeTime;

    @Column(name = "report_state")
    private Integer reportState;

    public void report() {
        this.reportState = 1;
    }
}