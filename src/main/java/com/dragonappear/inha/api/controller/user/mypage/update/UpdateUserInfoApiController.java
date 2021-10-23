package com.dragonappear.inha.api.controller.user.mypage.update;

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
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"마이페이지 유저 정보 수정 API"})
@RequiredArgsConstructor
@RestController
public class UpdateUserInfoApiController {
    private final UserService userService;
    private final UserImageService userImageService;
    private final UserAddressService userAddressService;
    private final UserAccountService userAccountService;

    @ApiOperation(value = "유저 닉네임 수정", notes = "유저 닉네임을 수정합니다.")
    @PostMapping("/users/update/nicknames/{userId} ")
    public String updateUserNickname(@PathVariable("userId") Long userId,@RequestParam("nickname") String nickname) {
        userService.updateNickname(userId, nickname);
        return nickname;
    }

    @ApiOperation(value = "유저 이름 수정", notes = "유저 이름를 수정합니다.")
    @PostMapping("/users/update/usernames/{userId}")
    public String updateUsername(@PathVariable("userId") Long userId,@RequestParam("username") String username) {
        userService.updateUsername(userId, username);
        return username;
    }

    @ApiOperation(value = "유저 전화번호 수정", notes = "유저 전화번호를 수정합니다.")
    @PostMapping("/users/update/userTels/{userId}")
    public String updateUserTel(@PathVariable("userId") Long userId, @RequestParam("userTel") String userTel) throws  Exception {
        userService.updateUserTel(userId, userTel);
        return userService.findOneById(userId).getUserTel();
    }

    @ApiOperation(value = "유저 프로필이미지 수정", notes = "유저 프로필을 수정합니다.")
    @PostMapping("/users/update/images/{userId}")
    public Map<String, Object> updateUserNickname(@PathVariable("userId") Long userId, @RequestBody ImageDto dto) throws  Exception {
        Result result = saveUserProfile(dto);
        if(result.getContent()==null) {
            return result.getMap();
        }
        userImageService.update(userService.findOneById(userId), (Image)result.getContent());
        return result.getMap();
    }

    @ApiOperation(value = "유저 정산 계좌 수정", notes = "유저 정산 계좌를 수정합니다.")
    @PostMapping("/users/update/accounts/{userId}")
    public Account updateUserAccount(@PathVariable("userId") Long userId, @RequestBody Account account) throws  Exception {
        return userAccountService.update(userService.findOneById(userId), account);
    }

    @ApiOperation(value = "유저 주소 수정", notes = "유저 주소를 수정합니다.")
    @PostMapping("/users/update/address/{userId}/{addressId}")
    public Address updateUserAddress(@PathVariable("userId") Long userId, @PathVariable("addressId") Long addressId,@RequestBody Address address) throws  Exception {
        return userAddressService.updateUserAddress(userId,addressId, address);
    }


    /**
     * base64 이미지 저장
     */
    public Result saveUserProfile(ImageDto dto) {
        Map<String,Object> result = new HashMap();
        String fileBase64 = dto.getFileBase64();
        Map<String, Object> validation = validateBase64(result, fileBase64);
        if(validation!=null){
            Result.builder().map(validation).content(null).build();
        }
        
        try {
            String sourceFileName = dto.getFileName();
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
            String fileUrl = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users";
            String destinationFileName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
            Image image = new Image(destinationFileName, sourceFileName, fileUrl);
            writeBase64ToFile(fileBase64, fileUrl, destinationFileName);
            return Result.builder()
                    .map(putResult("isFileInserted", true, "uploadStatus", "AllSuccess"))
                    .content(image)
                    .build();
        } catch (IOException e) {
                return Result.builder()
                    .map(putResult("isFileInserted", false, "uploadStatus", "FileIsNotUploaded"))
                    .content(null)
                    .build();
            }
    }

    public Map<String, Object> putResult(String insert, Boolean bool, String status, String content) {
        Map<String, Object> result = new HashMap<>();
        result.put(insert, bool);
        result.put(status, content);
        return result;
    }

    /**
     * 이미지 검증 로직
     */
    private Map<String, Object> validateBase64(Map<String, Object> result, String fileBase64) {
        if(fileBase64 == null || fileBase64.equals("")) {
            result.put("isFileInserted", false);
            result.put("uploadStatus", "FileIsNull");
            return result;
        } else if(fileBase64.length() > 400000) {
            result.put("isFileInserted", false);
            result.put("uploadStatus", "FileIsTooBig");
            return result;
        }
        return null;
    }

    /**
     * base64 디코더
     */
    private void writeBase64ToFile(String fileBase64, String fileUrl, String destinationFileName) throws IOException {
        File file = new File(fileUrl + destinationFileName);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(fileBase64.getBytes());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

    /**
     * DTO
     */

    @Data
    public class ImageDto{
        private String fileName;
        private String fileBase64;
    }

    @Data
    static class Result<T> {
        private Map<String, Object> map;
        private T content;
        @Builder
        public Result(Map<String, Object> map, T content) {
            this.map = map;
            this.content = content;
        }
    }


    /**
     * 이미지 저장
     */
    /*public Image saveUserImage(MultipartFile file) throws  Exception {
        String sourceFileName = file.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        String fileUrl = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users";

        String destinationFileName = RandomStringUtils.randomAlphabetic(32) + "." + sourceFileNameExtension;
        File destinationFile = new File(fileUrl + destinationFileName);
        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);
        return new Image(destinationFileName, sourceFileName, fileUrl);
    }*/
}
