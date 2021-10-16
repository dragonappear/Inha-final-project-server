package com.dragonappear.inha.api.controller.image;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
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

@RestController
public class ItemImageController {

    @GetMapping(value = "/images/items/{fileOriginName}")
    public ResponseEntity<Resource> itemImages(@PathVariable("fileOriginName") String fileOriginName) {
        String path = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static";
        String folder = "/items/";

        FileSystemResource resource = new FileSystemResource(path+folder+fileOriginName);
        if (!resource.exists()) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(path+folder+fileOriginName);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
}
