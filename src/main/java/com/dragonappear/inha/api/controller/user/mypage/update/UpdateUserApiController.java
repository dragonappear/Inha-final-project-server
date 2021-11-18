package com.dragonappear.inha.api.controller.user.mypage.update;

import com.dragonappear.inha.api.controller.user.mypage.update.dto.ImageDto;
import com.dragonappear.inha.domain.value.Account;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Image;
import com.dragonappear.inha.service.user.UserAccountService;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserImageService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.dragonappear.inha.api.controller.user.mypage.update.dto.ImageDto.getDto;

@Api(tags = {"마이페이지 유저 정보 수정 API"})
@RequiredArgsConstructor
@RestController
public class UpdateUserApiController {
    private final UserService userService;
    private final UserImageService userImageService;
    private final UserAddressService userAddressService;
    private final UserAccountService userAccountService;

    @ApiOperation(value = "유저 닉네임 수정 API", notes = "유저 닉네임을 수정")
    @PostMapping("/api/v1/users/update/nicknames/{userId} ")
    public String updateUserNickname(@PathVariable("userId") Long userId,@RequestParam("nickname") String nickname) {
        userService.updateNickname(userId, nickname);
        return nickname;
    }

    @ApiOperation(value = "유저 이름 수정 API", notes = "유저 이름를 수정")
    @PostMapping("/api/v1/users/update/usernames/{userId}")
    public String updateUsername(@PathVariable("userId") Long userId,@RequestParam("username") String username) {
        userService.updateUsername(userId, username);
        return username;
    }

    @ApiOperation(value = "유저 전화번호 수정 API", notes = "유저 전화번호를 수정")
    @PostMapping("/api/v1/users/update/userTels/{userId}")
    public String updateUserTel(@PathVariable("userId") Long userId, @RequestParam("userTel") String userTel) throws  Exception {
        userService.updateUserTel(userId, userTel);
        return userService.findOneById(userId).getUserTel();
    }

    @ApiOperation(value = "유저 프로필이미지 수정 API", notes = "유저 프로필을 수정")
    @PostMapping("/api/v1/users/update/images/{userId}")
    public Map<String, Object> updateUserProfile(@PathVariable("userId") Long userId, @RequestBody MultipartFile file)  {
        ImageDto imageDto = updateProfile(file);
        if(imageDto.getContent()==null) {
            return imageDto.getMap();
        }
        userImageService.update(userService.findOneById(userId), (Image) imageDto.getContent());
        return imageDto.getMap();
    }

    @ApiOperation(value = "유저 정산 계좌 수정 API", notes = "유저 정산 계좌를 수정")
    @PostMapping("/api/v1/users/update/accounts/{userId}")
    public Account updateUserAccount(@PathVariable("userId") Long userId, @RequestBody Account account) throws  Exception {
        return userAccountService.update(userService.findOneById(userId), account);
    }

    @ApiOperation(value = "유저 주소 수정 API", notes = "유저 주소를 수정")
    @PostMapping("/api/v1/users/update/address/{userId}/{addressId}")
    public Address updateUserAddress(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId,@RequestBody Address address) throws  Exception {
        return userAddressService.updateUserAddress(userId,addressId, address);
    }

    /**
     * 이미지 저장
     */
    public ImageDto updateProfile(MultipartFile file)  {
        try {
            String sourceFileName = file.getOriginalFilename();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            String fileUrl = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users/";
            //String fileUrl = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static/users/";
            String destinationFileName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
            File destinationFile = new File(fileUrl + destinationFileName);
            destinationFile.getParentFile().mkdirs();
            file.transferTo(destinationFile);
            Image image = new Image(destinationFileName, sourceFileName, fileUrl);
            return ImageDto.builder()
                    .map(getDto("isFileInserted", true, "uploadStatus", "AllSuccess"))
                    .content(image)
                    .build();
        } catch (Exception e) {
            return ImageDto.builder()
                    .map(getDto("isFileInserted", false, "uploadStatus", "FileIsNotUploaded"))
                    .content(null)
                    .build();
        }
    }

}
