package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.domain.user.UserImage;
import com.dragonappear.inha.service.user.UserImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = {"유저 이미지 API"})
@RequiredArgsConstructor
@RestController
public class UserImageApiController {
    private final UserImageService userImageService;

    @ApiOperation(value = "유저 이미지 조회 by 이미지이름으로", notes = "이미지를 반환합니다.")
    @GetMapping(value = "/users/images/{fileOriginName}")
    public ResponseEntity<Resource> getItemImagesByFileName(@PathVariable("fileOriginName") String fileOriginName) {
        String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users/";
        FileSystemResource resource = new FileSystemResource(path+fileOriginName);
        if (!resource.exists()) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(path+fileOriginName);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }

    @ApiOperation(value = "유저 이미지 조회 by 이미지이름으로", notes = "이미지를 반환합니다.")
    @GetMapping(value = "/users/images/find/{userId}")
    public ResponseEntity<Resource> getItemImagesByUserId(@PathVariable("userId") Long userId) {
        String fileOriginName = userImageService.findByUserId(userId).getImage().getFileName();
        String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users/";
        //String path = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static/users/";
        FileSystemResource resource = new FileSystemResource(path+fileOriginName);
        if (!resource.exists()) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(path+fileOriginName);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
}
