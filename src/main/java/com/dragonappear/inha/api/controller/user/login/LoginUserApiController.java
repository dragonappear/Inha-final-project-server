package com.dragonappear.inha.api.controller.user.login;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.user.login.dto.SaveUserInfoDto;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"로그인 유저 정보 API"})
@RequiredArgsConstructor
@RestController
public class LoginUserApiController {
    private final UserService userService;
    private final UserAddressService userAddressService;
    private final UserImageService userImageService;
    private final UserPointService userPointService;
    private final UserAccountService userAccountService;
    
    @ApiOperation(value = "유저 정보 조회 API", notes = "유저 정보 조회")
    @GetMapping(value = "/users/{email}")
    public MessageDto loginUserInfoDto(@PathVariable("email") String email) {
        Long id=0L;
        String role = "";
        try {
            id = userService.findOneByEmail(email).getId();
            role = userService.findOneById(id).getUserRole().toString();
        } catch (Exception e) {
            return MessageDto.builder()
                    .message(getMessage("isRegistered", false, "id", null,"role",e.getMessage()))
                    .build();
        }
        return MessageDto.builder()
                .message(getMessage("isRegistered", true, "id", id,"role",role))
                .build();
    }
    
    @ApiOperation(value = "유저 정보 저장 API", notes = "유저 정보 저장")
    @PostMapping(value = "/users/new")
    public SaveUserInfoDto saveUserInfo(@RequestBody SaveUserInfoDto userInfoDto) {
        return saveUserInfoDto(userInfoDto);
    }

    /**
     *  유저 DB 저장 로직
     */
    private SaveUserInfoDto saveUserInfoDto(SaveUserInfoDto userInfoDto) {
        Long id = userService.join(userBuilder(userInfoDto));
        return userInfoService(userInfoDto, id);
    }

    /**
     * 유저 빌더 로직
     */
    private User userBuilder(SaveUserInfoDto userInfoDto) {
        return User.builder()
                .email(userInfoDto.getEmail())
                .userTel(userInfoDto.getUserTel())
                .username(userInfoDto.getUsername())
                .nickname(userInfoDto.getNickname())
                .build();
    }

    /**
     *  유저 서비스 로직
     */
    private SaveUserInfoDto userInfoService(SaveUserInfoDto userInfoDto, Long id) {
        User user = userService.findOneById(id);

        userAddressService.save(user.getId(), userInfoDto.getAddress());
        userImageService.update(user
                , new Image("basic icon","NequEEEQWEeqweZXCZXCZASDsitas.png", "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/user"));
        userPointService.create(user.getId());
        userAccountService.update(user,
                new Account(userInfoDto.getAccount().getBankName(), userInfoDto.getAccount().getAccountNumber(), userInfoDto.getAccount().getAccountHolder()));

        return userInfoDto;
    }


}
