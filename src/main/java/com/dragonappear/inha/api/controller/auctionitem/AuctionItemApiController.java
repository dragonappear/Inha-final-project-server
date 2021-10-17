package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.api.controller.auctionitem.dto.ItemDto;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"상품 조회 API"})
@RestController
@RequiredArgsConstructor
public class AuctionItemApiController {
    private final ItemService itemService;

    @ApiOperation(value = "전체 아이템 조회", notes = "모든 아이템을 조회합니다.")
    @GetMapping("/api/v2/items")
    public List<ItemDto> allItems() {
        return itemService.findAll()
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                ,item.getItemImages().get(0).getItemImage().getFileOriName()
                                , item.getManufacturer().getManufacturerName()
                                , item.getItemName()
                                , item.getLikeCount()
                                , item.getLatestPrice().getAmount())).
                collect(Collectors.toList());
    }

    @ApiOperation(value = "카테고리 내 전체 아이템 조회", notes = "카테고리 내 전체 아이템을 조회합니다.")
    @GetMapping("/api/v2/items/{categoryName}")
    public List<ItemDto> categoryItems(@PathVariable(name = "categoryName") CategoryName categoryName) {
        return itemService.findByCategoryName(categoryName)
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                ,item.getItemImages().get(0).getItemImage().getFileOriName()
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLikeCount()
                                , item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "카테고리+제조사 내 전체 아이템 조회", notes = "카테고리+제조사 내 전체 아이템을 조회합니다.")
    @GetMapping("/api/v2/items/{categoryName}/{manufacturerName}")
    public List<ItemDto> categoryItems(@PathVariable(name = "categoryName") CategoryName categoryName
            , @PathVariable(name = "manufacturerName")ManufacturerName manufacturerName) {
        return itemService.findByCategoryAndManufacturer(categoryName,manufacturerName)
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                , item.getItemImages().get(0).getItemImage().getFileOriName()
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLikeCount()
                                , item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());
    }
}
