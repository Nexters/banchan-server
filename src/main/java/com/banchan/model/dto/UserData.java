package com.banchan.model.dto;

import com.banchan.model.entity.User;
import lombok.Data;

import java.util.Optional;

@Data
public class UserData {

    User user;
    Double speaker;

    public UserData(User user, Double speaker) {
        this.user = user;
        this.speaker = Optional.ofNullable(speaker).orElse(0.0);
    }
}
