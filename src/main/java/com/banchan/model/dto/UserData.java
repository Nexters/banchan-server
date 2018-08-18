package com.banchan.model.dto;

import com.banchan.model.entity.User;
import lombok.Data;

@Data
public class UserData {

    User user;
    Double speaker;

    public UserData(User user, Double speaker) {
        this.user = user;
        this.speaker = speaker;
    }
}
