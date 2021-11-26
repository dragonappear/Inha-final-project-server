package com.dragonappear.inha.api.controller.item.shoppage;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.dragonappear.inha.api.controller.auctionitem.dto.ItemDto;
import com.dragonappear.inha.api.controller.auctionitem.dto.SimpleItemDto;
import com.dragonappear.inha.api.repository.item.NotebookQueryRepository;
import com.dragonappear.inha.api.repository.item.dto.*;
import com.dragonappear.inha.api.returndto.DetailDto;
import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.product.*;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.service.item.ItemImageService;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = {"아이템 정보 조회 API"})
@RestController
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;
    private final NotebookQueryRepository notebookQueryRepository;
    private final ItemImageService itemImageService;
    private  Map<Class< ? extends Item>,Class< ? extends DetailItemDto>> itemMap = new HashMap<>();

    @PostConstruct
    public void init() {
        itemMap.put(Notebook.class, NotebookApiDto.class);
        itemMap.put(Monitor.class, MonitorApiDto.class);
        itemMap.put(SmartPhone.class, SmartPhoneApiDto.class);
        itemMap.put(Keyboard.class, KeyboardApiDto.class);
        itemMap.put(Tablet.class, TabletApiDto.class);
    }

    @ApiOperation(value = "전체 아이템 조회 API", notes = "모든 아이템을 조회")
    @GetMapping("/api/v1/items")
    public ResultDto allItems() {
        List<ItemDto> items = itemService.findAll().stream()
                .map(item -> new ItemDto(item.getId()
                                , (item.getItemImages().size()==0)? null : item.getItemImages().get(0).getItemImage().getFileName()
                                , item.getManufacturer().getManufacturerName()
                                , item.getItemName()
                                , item.getLikeCount()
                                , (item.getLatestPrice()==null) ? null : item.getLatestPrice().getAmount())).
                collect(Collectors.toList());
        return ResultDto.builder()
                .count(items.size())
                .content(items.stream().map(itemDto -> {return (Object)itemDto;}).collect(Collectors.toList())  )
                .build();
    }

    @ApiOperation(value = "카테고리 내 전체 아이템 조회 API", notes = "카테고리 내 전체 아이템을 조회")
    @GetMapping("/api/v1/items/{categoryName}")
    public ResultDto categoryItems(@PathVariable(name = "categoryName") CategoryName categoryName) {
        List<ItemDto> items = itemService.findByCategoryName(categoryName)
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                , item.getItemImages().get(0).getItemImage().getFileName()
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLikeCount()
                                , (item.getLatestPrice()==null) ? null : item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());

        return ResultDto.builder()
                .count(items.size())
                .content(items.stream().map(itemDto -> {return (Object)itemDto;}).collect(Collectors.toList())  )
                .build();
    }

    @ApiOperation(value = "카테고리+제조사 내 전체 아이템 조회 API", notes = "카테고리+제조사 내 전체 아이템을 조회")
    @GetMapping("/api/v1/items/{categoryName}/{manufacturerName}")
    public ResultDto categoryManufacturerItems(@PathVariable(name = "categoryName") CategoryName categoryName
            , @PathVariable(name = "manufacturerName") ManufacturerName manufacturerName) {
        List<ItemDto> items = itemService.findByCategoryAndManufacturer(categoryName,manufacturerName)
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                , item.getItemImages().size()==0 ? null: item.getItemImages().get(0).getItemImage().getFileName()
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLikeCount()
                                , (item.getLatestPrice()==null) ? null : item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());

        return ResultDto.builder()
                .count(items.size())
                .content(items.stream().map(itemDto -> {return (Object)itemDto;}).collect(Collectors.toList())  )
                .build();
    }

    @ApiOperation(value = "아이템 상세 조회 API", notes = "아이템 상세 조회")
    @GetMapping("/api/v1/items/details/{itemId}")
    public DetailDto detailItem(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findByItemId(itemId);
        List<String> names = itemImageService.findByItemId(itemId).stream().map(image -> image.getItemImage().getFileName()).collect(Collectors.toList());
        for (Class<? extends Item> itemType : itemMap.keySet()) {
            if (item.getClass().toString().equals(itemType.toString())) {
            }
        }
        if (item instanceof Notebook) {
            NotebookApiDto dto = notebookQueryRepository.findById(itemId);
            return DetailDto.builder()
                    .fileNames(names)
                    .detail(dto)
                    .build();
        }
        return null;

    }

    @ApiOperation(value = "판매전, 구매전 아이템 대표정보 조회 API", notes = "판매전, 구매전 아이템 대표정보 조회")
    @GetMapping("/api/v1/items/simple/{itemId}")
    public SimpleItemDto simpleItemDto(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findByItemId(itemId);
        return SimpleItemDto.builder()
                .itemName(item.getItemName())
                .manufacturerName(item.getManufacturer().getManufacturerName())
                .modelNumber(item.getModelNumber())
                .fileOriName(item.getItemImages().get(0).getItemImage().getFileName())
                .build();
    }
}
