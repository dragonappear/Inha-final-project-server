package com.dragonappear.inha.api.controller.inspection;


import com.dragonappear.inha.api.controller.inspection.dto.SearchDto;
import com.dragonappear.inha.api.controller.inspection.dto.InspectionApiDto;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.exception.NotFoundImageException;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.inspection.InspectionImageService;
import com.dragonappear.inha.service.selling.SellingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = {"검수 조회 API"})
@RestController
@RequiredArgsConstructor
public class InspectionApiController {
    private final InspectionImageService inspectionImageService;
    private final SellingService sellingService;
    private final BuyingService buyingService;

    @ApiOperation(value = "검수 조회 API by 거래아이디로", notes = "거래 조회")
    @PostMapping("/inspections")
    public InspectionApiDto getInspectionResult(@RequestBody SearchDto dto) {
        Inspection inspection;
        if (dto.getBuyingId() == null) {
            Selling selling = sellingService.findBySellingId(dto.getSellingId());
            inspection = selling.getDeal().getInspection();
        }else{
            Buying buying = buyingService.findById(dto.getBuyingId());
            inspection = buying.getDeal().getInspection();
        }
        List<String> fileNames = inspectionImageService.findByInspectionId(inspection.getId()).stream()
                .map(inspectionImage -> {
                    return inspectionImage.getInspectionImage().getFileName();
                }).collect(Collectors.toList());

        return InspectionApiDto.builder()
                .fileNames(fileNames)
                .inspection(inspection)
                .build();
    }

    @ApiOperation(value = "검수결과 이미지 조회 API by 파일이름으로", notes = "검수결과 이미지 조회")
    @GetMapping(value = "/inspections/images/{fileName}")
    public ResponseEntity<Resource> getInspectionImageByName(@PathVariable("fileName") String fileName) {
        try {
            String path = "/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/static/inspections/";
            //String path = "/Users/dragonappear/Documents/study/inha_document/컴퓨터종합설계/code/inha/src/main/resources/static/inspections/";
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
