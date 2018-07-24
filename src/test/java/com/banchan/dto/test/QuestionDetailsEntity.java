package com.banchan.dto.test;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "question_details", schema = "nanigo", catalog = "")
public class QuestionDetailsEntity {
    private int id;
    private String type;
    private String content;
    private int questionId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDetailsEntity that = (QuestionDetailsEntity) o;
        return id == that.id &&
                questionId == that.questionId &&
                Objects.equals(type, that.type) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, content, questionId);
    }
}
