package com.banchan.dto.test;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usernames", schema = "nanigo", catalog = "")
public class UsernamesEntity {
    private int id;
    private String prefix;
    private String postfix;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "prefix")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "postfix")
    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsernamesEntity that = (UsernamesEntity) o;
        return id == that.id &&
                Objects.equals(prefix, that.prefix) &&
                Objects.equals(postfix, that.postfix);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, prefix, postfix);
    }
}
