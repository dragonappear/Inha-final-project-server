package com.dragonappear.inha.api.controller.user;


import com.dragonappear.inha.config.auth.LoginUser;
import com.dragonappear.inha.config.auth.dto.SessionUser;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.value.UserRole;
import com.dragonappear.inha.service.user.UserService;
import com.dragonappear.inha.api.controller.user.dto.SessionUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"세션 유저 API"})
@RequiredArgsConstructor
@RestController
public class SessionUserController {
    private final UserService userService;

    @ApiOperation(value = "세션 유저 정보 조회", notes = "세션 유저 정보를 반환합니다.")
    @GetMapping(value = "/users")
    public SessionUserDto sessionUser(@LoginUser SessionUser user) {
        User findUser = userService.findOneByEmail(user.getEmail());
        if (findUser == null) {
            findUser = User.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .userRole(UserRole.USER)
                    .picture(user.getPicture())
                    .build();
            userService.join(findUser);
        }
        return SessionUserDto.builder()
                .email(findUser.getEmail())
                .username(findUser.getUsername())
                .userAddresses(findUser.getUserAddresses())
                .userTel(findUser.getUserTel())
                .build();
    }
}
