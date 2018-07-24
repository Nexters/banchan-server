package com.banchan.dto.test;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "questions", schema = "nanigo", catalog = "")
public class QuestionsEntity {
    private int id;
    private int userId;
    private Timestamp writeTime;

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
    @Column(name = "write_time")
    public Timestamp getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Timestamp writeTime) {
        this.writeTime = writeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionsEntity that = (QuestionsEntity) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(writeTime, that.writeTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, writeTime);
    }
}
