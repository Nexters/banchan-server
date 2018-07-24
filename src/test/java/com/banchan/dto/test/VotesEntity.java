package com.banchan.dto.test;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "votes", schema = "nanigo", catalog = "")
public class VotesEntity {
    private int id;
    private int userId;
    private String questionId;
    private byte answer;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "question_id")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "answer")
    public byte getAnswer() {
        return answer;
    }

    public void setAnswer(byte answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotesEntity that = (VotesEntity) o;
        return id == that.id &&
                userId == that.userId &&
                answer == that.answer &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, questionId, answer);
    }
}
