package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.repository.item.CategoryManufacturerRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"카테고리 API"})
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryManufacturerRepository categoryManufacturerRepository;


    @ApiOperation(value = "모든 카테고리 조회", notes = "모든 카테고리를 조회합니다.")
    @GetMapping("/api/v1/categories")
    public List<CategoryName> categoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> category.getCategoryName())
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "카테고리 내 모든 제조사 조회", notes = "카테고리 내 모든 제조사를 조회합니다.")
    @GetMapping("/api/v1/{categoryName}/manufacturers")
    public List<ManufacturerName> manufacturerNames(@PathVariable(name = "categoryName") CategoryName categoryName) {
        return categoryManufacturerRepository.findByCategoryName(categoryName)
                .stream()
                .collect(Collectors.toList());
    }

}
