package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.repository.item.CategoryManufacturerRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryManufacturerRepository categoryManufacturerRepository;

    @GetMapping("/api/v2/categories")
    public List<CategoryName> categoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> category.getCategoryName())
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v2/{categoryName}/manufacturers")
    public List<ManufacturerName> manufacturerNames(@PathVariable(name = "categoryName") CategoryName categoryName) {
        return categoryManufacturerRepository.findByCategoryName(categoryName)
                .stream()
                .collect(Collectors.toList());
    }

}
