package com.dragonappear.inha.api.controller.user;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.domain.user.value.UserRole;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserImageService;
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
    private final UserImageService userImageService;
    
    @ApiOperation(value = "유저 정보 조회", notes = "유저 이름, 번호, 주소 조회")
    @GetMapping(value = "/users/{email}")
    public UserInfoDto findUserInfo(@PathVariable("email") String email) {
        User findUser = userService.findOneByEmail(email);
        if (findUser== null) {
            return getUserInfoDto(null, null, null, null,null,null,null);
        }
        return getUserInfoDto(findUser.getUsername()
                ,findUser.getNickname()
                , findUser.getEmail()
                , findUser.getUserTel()
                , (findUser.getUserAddresses().size()==0) ? null : findUser.getUserAddresses().get(0).getUserAddress()
                , (findUser.getUserImage()==null) ? null : findUser.getUserImage().getImage().getFileOriName()
                , findUser.getUserRole());
    }
    
    @ApiOperation(value = "유저 정보 저장", notes = "유저 휴대폰 정보, 주소 저장")
    @PostMapping(value = "/users/new")
    public void saveUserInfo(UserInfoDto userInfoDto) {
        Long id = userService.join(
                User.builder()
                        .email(userInfoDto.getEmail())
                        .userTel(userInfoDto.getUserTel())
                        .username(userInfoDto.getUsername())
                        .nickname(userInfoDto.getNickname())
                        .build()
        );
        User user = userService.findOneById(id);
        userAddressService.save(user, userInfoDto.getAddress());
        userImageService.update(user
                , new Image("basic icon","profile.png", "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/user"));
    }


    /**
     * get DTO
     */
    private UserInfoDto getUserInfoDto(String userName,String nickname, String email, String userTel, Address address, String userProfileUrl,UserRole userRole) {
        return UserInfoDto.builder()
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
