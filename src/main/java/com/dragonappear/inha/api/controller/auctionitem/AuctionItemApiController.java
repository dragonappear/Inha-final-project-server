package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.api.controller.auctionitem.dto.ItemDto;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.service.item.ItemImageService;
import com.dragonappear.inha.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuctionItemApiController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;

    @GetMapping("/items")
    public List<ItemDto> allItems() {
        return itemService.findAll()
                .stream()
                .map(item ->
                        new ItemDto(item.getItemImages().stream().map(image -> image.getItemImage().getFileUrl()).collect(Collectors.toList())
                                , item.getManufacturer().getManufacturerName()
                                , item.getItemName()
                                , item.getLatestPrice().getAmount())).
                collect(Collectors.toList());
    }

    @GetMapping("/items/{categoryName}")
    public List<ItemDto> categoryItems(@PathVariable(name = "categoryName") CategoryName categoryName) {
        return itemService.findByCategoryName(categoryName)
                .stream()
                .map(item ->
                        new ItemDto(item.getItemImages().stream().map(image -> image.getItemImage().getFileUrl()).collect(Collectors.toList())
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());
    }

    @GetMapping("/items/{categoryName}/{manufacturerName}")
    public List<ItemDto> categoryItems(@PathVariable(name = "categoryName") CategoryName categoryName
            , @PathVariable(name = "manufacturerName")ManufacturerName manufacturerName) {
        return itemService.findByCategoryAndManufacturer(categoryName,manufacturerName)
                .stream()
                .map(item ->
                        new ItemDto(item.getItemImages().stream().map(image -> image.getItemImage().getFileUrl()).collect(Collectors.toList())
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());
    }
}
