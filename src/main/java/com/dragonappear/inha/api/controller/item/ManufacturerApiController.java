package com.dragonappear.inha.api.controller.item;

import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.repository.item.ManufacturerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"아이템 제조사 API"})
@RestController
@RequiredArgsConstructor
public class ManufacturerApiController {
    private final ManufacturerRepository manufacturerRepository;

    @ApiOperation(value = "모든 제조사 조회 API", notes = "모든 제조사 조회")
    @GetMapping("/api/v1/items/manufacturers")
    public List<ManufacturerName> manufacturerNames() {
        return manufacturerRepository.findAll()
                .stream()
                .map(manufacturer -> manufacturer.getManufacturerName())
                .collect(Collectors.toList());
    }
}
