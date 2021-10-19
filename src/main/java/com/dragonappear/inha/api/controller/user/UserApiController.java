package com.dragonappear.inha.api.controller.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserService;
import com.dragonappear.inha.api.controller.user.dto.UserInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"유저 정보 API"})
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;
    private final UserAddressService userAddressService;

    @ApiOperation(value = "유저 정보 저장", notes = "유저 휴대폰 정보, 주소 저장")
    @PostMapping(value = "/users/new")
    public void saveUserInfo(UserInfoDto userInfoDto) {
        User findUser = userService.findOneByEmail(userInfoDto.getEmail());
        findUser.updateUserTel(userInfoDto.getUserTel());
        userAddressService.save(findUser, userInfoDto.getAddress());
    }

    @ApiOperation(value = "구매전 유저 정보 조회", notes = "유저 이름, 번호, 주소 조회")
    @GetMapping(value = "/users/{email}")
    public UserInfoDto saveUserInfo(@PathVariable("email") String email) {
        User findUser = userService.findOneByEmail(email);
        return UserInfoDto.builder()
                .username(findUser.getUsername())
                .email(findUser.getEmail())
                .userTel(findUser.getUserTel())
                .address(findUser.getUserAddresses().get(0).getUserAddress())
                .build();
    }
}
