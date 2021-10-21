package com.dragonappear.inha.api.controller.user.login;

import com.dragonappear.inha.api.controller.user.login.dto.SaveUserInfoDto;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.value.UserRole;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.user.*;
import com.dragonappear.inha.api.controller.user.login.dto.LoginUserInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;

@Api(tags = {"로그인 유저 정보 API"})
@RequiredArgsConstructor
@RestController
public class LoginUserApiController {
    private final UserService userService;
    private final UserAddressService userAddressService;
    private final UserImageService userImageService;
    private final UserPointService userPointService;
    private final UserAccountService userAccountService;
    
    @ApiOperation(value = "유저 정보 조회", notes = "유저 이름, 번호, 주소 조회")
    @GetMapping(value = "/users/{email}")
    public LoginUserInfoDto loginUserInfoDto(@PathVariable("email") String email) {
        User findUser = userService.findOneByEmail(email);
        if (findUser== null) {
            return getUserInfoDto(null,null, null, null, null,null,null,null);
        }
        return getUserInfoDto(findUser.getId()
                ,findUser.getUsername()
                ,findUser.getNickname()
                , findUser.getEmail()
                , findUser.getUserTel()
                , (findUser.getUserAddresses().size()==0) ? null : findUser.getUserAddresses().get(0).getUserAddress()
                , (findUser.getUserImage()==null) ? null : findUser.getUserImage().getImage().getFileOriName()
                , findUser.getUserRole());
    }
    
    @ApiOperation(value = "유저 정보 저장", notes = "유저 휴대폰 정보, 주소 저장")
    @PostMapping(value = "/users/new")
    public SaveUserInfoDto saveUserInfo(@RequestBody SaveUserInfoDto userInfoDto) {
        Long id = userService.join(
                User.builder()
                        .email(userInfoDto.getEmail())
                        .userTel(userInfoDto.getUserTel())
                        .username(userInfoDto.getUsername())
                        .nickname(userInfoDto.getNickname())
                        .build()
        );

        User user = userService.findOneById(id);
        userAddressService.save(user.getId(), userInfoDto.getAddress());
        userImageService.update(user
                , new Image("basic icon","profile.png", "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/user"));
        userPointService.create(user.getId());
        userAccountService.update(user,
                new Account(userInfoDto.getAccount().getBankName(), userInfoDto.getAccount().getAccountNumber(), userInfoDto.getAccount().getAccountHolder()));
        return userInfoDto;
    }



    /**
     * get DTO
     */
    private LoginUserInfoDto getUserInfoDto(Long userId, String userName, String nickname, String email, String userTel, Address address, String userProfileUrl, UserRole userRole) {
        return LoginUserInfoDto.builder()
                .userId(userId)
                .username(userName)
                .nickname(nickname)
                .email(email)
                .userTel(userTel)
                .address(address)
                .userImageUrl(userProfileUrl)
                .userRole(userRole)
                .build();
    }
}
