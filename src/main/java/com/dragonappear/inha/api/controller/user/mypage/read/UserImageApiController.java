package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.exception.NotFoundImageException;
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
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = {"마이페이지 유저 이미지 API"})
@RequiredArgsConstructor
@RestController
public class UserImageApiController {
    private final UserImageService userImageService;

    @ApiOperation(value = "유저 이미지 조회 API by 이미지이름으로", notes = "이미지 조회")
    @GetMapping(value = "/users/images/{fileOriginName}")
    public ResponseEntity<Resource> getItemImagesByFileName(@PathVariable("fileOriginName") String fileOriginName) {
        try {
            String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users/";
            FileSystemResource resource = new FileSystemResource(path+fileOriginName);
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;

            filePath = Paths.get(path+fileOriginName);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }

    @ApiOperation(value = "유저 이미지 조회 API by 유저아이디로", notes = "이미지 조회")
    @GetMapping(value = "/users/images/find/{userId}")
    public ResponseEntity<Resource> getItemImagesByUserId(@PathVariable("userId") Long userId) {
        try {
            String fileOriginName = userImageService.findByUserId(userId).getImage().getFileName();
            String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/users/";
            //String path = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static/users/";
            FileSystemResource resource = new FileSystemResource(path+fileOriginName);
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path+fileOriginName);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }
}
