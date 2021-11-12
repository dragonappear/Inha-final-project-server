package com.dragonappear.inha.api.controller.item;

import com.dragonappear.inha.exception.NotFoundImageException;
import com.dragonappear.inha.service.item.ItemImageService;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = {"아이템 이미지 조회 API"})
@RequiredArgsConstructor
@RestController
public class ItemImageApiController {
    private final ItemImageService itemImageService;

    @ApiOperation(value = "아이템 대표 이미지 조회 API by 파일이름으로", notes = "아이템 이미지 조회")
    @GetMapping(value = "/items/images/{fileOriginName}")
    public ResponseEntity<Resource> getItemImageByName(@PathVariable("fileOriginName") String fileName) {
        try {
            String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/items/";
            FileSystemResource resource = new FileSystemResource(path+fileName);
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path+fileName);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }

    @ApiOperation(value = "아이템 대표 이미지 조회 API by 아이템아이디로", notes = "아이템 이미지 조회")
    @GetMapping(value = "/items/images/find/{itemId}")
    public ResponseEntity<Resource> getItemImageById(@PathVariable("itemId") Long itemId) {
        try {
            String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/items/";
            String fileName = itemImageService.findByItemId(itemId).get(0).getItemImage().getFileName();
            FileSystemResource resource = new FileSystemResource(path+fileName);
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(path+fileName);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }

    }
}
