package com.banchan.model.entity;

import com.banchan.model.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Reviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "report_state")
    private Integer reportState;

    @Column(name = "delete_state")
    private Integer deleteState;

    @Builder
    public Reviews(Integer questionId, User user, String content,
                   Integer reportState, Integer deleteState) {
        this.questionId = questionId;
        this.user = user;
        this.content = content;
        this.reportState = reportState;
        this.deleteState = deleteState;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.deleteState = 1;
    }

    public void report() {
        this.reportState = 1;
    }
}

