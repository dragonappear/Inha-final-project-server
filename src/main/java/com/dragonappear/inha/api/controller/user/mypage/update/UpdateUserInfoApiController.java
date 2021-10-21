package com.dragonappear.inha.api.controller.user.mypage.update;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserImageService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Api(tags = {"마이페이지 유저 정보 업데이트 API"})
@RequiredArgsConstructor
@RestController
public class UpdateUserInfoApiController {
    private final UserService userService;
    private final UserImageService userImageService;
    private final UserAddressService userAddressService;

    @ApiOperation(value = "유저 닉네임 업데이트", notes = "유저 닉네임을 업데이트합니다.")
    @PostMapping("/users/update/nicknames/{userId} ")
    public String updateUserNickname(@PathVariable("userId") Long userId,@RequestParam("nickname") String nickname) {
        userService.updateNickname(userId, nickname);
        return nickname;
    }

    @ApiOperation(value = "유저 이름 업데이트", notes = "유저 이름를 업데이트합니다.")
    @PostMapping("/users/update/usernames/{userId}")
    public String updateUsername(@PathVariable("userId") Long userId,@RequestParam("username") String username) {
        userService.updateUsername(userId, username);
        return username;
    }

    @ApiOperation(value = "유저 전화번호 업데이트", notes = "유저 전화번호를 업데이트합니다.")
    @PostMapping("/users/update/userTels/{userId}")
    public String updateUserTel(@PathVariable("userId") Long userId, @RequestParam("userTel") String userTel) throws  Exception {
        userService.updateUserTel(userId, userTel);
        return userService.findOneById(userId).getUserTel();
    }

    @ApiOperation(value = "유저 프로필 업데이트", notes = "유저 프로필을 업데이트합니다.")
    @PostMapping("/users/update/images/{userId}")
    public String updateUserNickname(@PathVariable("userId") Long userId, HttpServletRequest request, @RequestPart MultipartFile file) throws  Exception {
        return userImageService.update(userService.findOneById(userId),saveUserImage(file));
    }


    /**
     * 이미지 저장
     */
    public Image saveUserImage(MultipartFile file) throws  Exception {
        String sourceFileName = file.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        File destinationFile;
        String destinationFileName;
        String fileUrl = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users";
        destinationFileName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
        destinationFile = new File(fileUrl + destinationFileName);
        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);
        return new Image(destinationFileName, sourceFileName, fileUrl);
    }

}
