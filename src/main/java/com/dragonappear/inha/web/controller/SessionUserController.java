package com.dragonappear.inha.web.controller;


import com.dragonappear.inha.config.auth.LoginUser;
import com.dragonappear.inha.config.auth.dto.SessionUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"세션 유저 API"})
@RestController
public class SessionUserController {
    @ApiOperation(value = "세션 유저 정보 조회", notes = "세션 유저 정보를 반환합니다.")
    @GetMapping(value = "/")
    public SessionUser sessionUser(@LoginUser SessionUser user) {
        return user;
    }
}
