package com.dragonappear.inha.web.controller;

import com.dragonappear.inha.config.auth.LoginUser;
import com.dragonappear.inha.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/home")
    public String index(@LoginUser SessionUser sessionUser) {
        return sessionUser.getUsername();
    }
}
