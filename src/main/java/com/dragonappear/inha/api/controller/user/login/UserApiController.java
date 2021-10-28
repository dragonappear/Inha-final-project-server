package com.dragonappear.inha.api.controller.user.login;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.user.login.dto.UserDto;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"유저 정보 API"})
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;
    private final UserAddressService userAddressService;
    private final UserImageService userImageService;
    private final UserPointService userPointService;
    private final UserAccountService userAccountService;
    
    @ApiOperation(value = "유저 등록 조회 API By 유저이메일", notes = "유저 정보 조회")
    @GetMapping(value = "/users/{email}")
    public MessageDto checkRegistered(@PathVariable("email") String email) {
        User user = userService.findOneByEmail(email);
        return MessageDto.builder()
                .message(getMessage("isRegistered", true, "id", user.getId(),"role",user.getUserRole().toString()))
                .build();
    }

    @ApiOperation(value = "유저 정보 조회 API By 유저아이디", notes = "유저 정보 조회")
    @GetMapping(value = "/users/find/{userId}")
    public UserDto getUserInfo(@PathVariable("userId") Long userId) {
        User user = userService.findOneById(userId);
        return new UserDto(user);

    }

    @ApiOperation(value = "유저 정보 저장 API", notes = "유저 정보 저장")
    @PostMapping(value = "/users/new")
    public UserDto save(@RequestBody UserDto userDto) {
        return createUser(userDto);
    }

    /**
     *  유저 DB 저장 로직
     */
    private UserDto createUser(UserDto userDto) {
        User user = userDto.toEntity();
        userService.join(user);
        userAddressService.save(user.getId(), userDto.getAddress()); // 유저 주소 저장
        userImageService.update(user // 유저 이미지 저장
                , new Image("basic icon","NequEEEQWEeqweZXCZXCZASDsitas.png", "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/user"));
        userPointService.create(user.getId()); // 유저 포인트 초기 생성
        userAccountService.update(user, // 유저 주소 저장
                new Account(userDto.getAccount().getBankName(), userDto.getAccount().getAccountNumber(), userDto.getAccount().getAccountHolder()));
        return userDto;
    }
}
