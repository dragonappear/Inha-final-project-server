package com.dragonappear.inha.api.controller.user.login;


import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"세션 유저 API"})
@RequiredArgsConstructor
@RestController
public class SessionUserController {
    private final UserService userService;

/*    @ApiOperation(value = "세션 유저 정보 조회", notes = "세션 유저 정보를 반환합니다.")
    @GetMapping(value = "/users")
    public SessionUserDto sessionUser(@LoginUser SessionUser user) {
        User findUser = userService.findOneByEmail(user.getEmail());
        if (findUser == null) {
            findUser = User.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
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
    }*/
}
