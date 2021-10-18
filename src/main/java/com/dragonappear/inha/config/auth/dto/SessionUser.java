package com.dragonappear.inha.config.auth.dto;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.value.UserRole;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    private String picture;
    private UserRole userRole;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.userRole = user.getUserRole();
    }
}
