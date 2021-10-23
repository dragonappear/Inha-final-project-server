package com.dragonappear.inha.api.controller.item;

import com.dragonappear.inha.api.controller.auctionitem.dto.ItemDto;
import com.dragonappear.inha.api.controller.auctionitem.dto.SimpleItemDto;
import com.dragonappear.inha.api.repository.deal.DealQueryRepository;
import com.dragonappear.inha.api.repository.deal.dto.MarketPriceInfoDto;
import com.dragonappear.inha.api.repository.item.dto.NotebookDto;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.value.CategoryName;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.api.repository.item.NotebookQueryRepository;
import com.dragonappear.inha.service.item.ItemImageService;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"상품 조회 API"})
@RestController
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;
    private final DealQueryRepository dealQueryRepository;
    private final NotebookQueryRepository notebookQueryRepository;
    private final ItemImageService itemImageService;

    @ApiOperation(value = "전체 아이템 조회", notes = "모든 아이템을 조회합니다.")
    @GetMapping("/items")
    public Results allItems() {
        List<ItemDto> items = itemService.findAll().stream()
                .map(item -> new ItemDto(item.getId()
                                , item.getItemImages().get(0).getItemImage().getFileName()
                                , item.getManufacturer().getManufacturerName()
                                , item.getItemName()
                                , item.getLikeCount()
                                , item.getLatestPrice().getAmount())).
                collect(Collectors.toList());

        return Results.builder()
                .count(items.size())
                .items(items.stream().map(itemDto -> {return (Object)itemDto;}).collect(Collectors.toList())  )
                .build();
    }

    @ApiOperation(value = "카테고리 내 전체 아이템 조회", notes = "카테고리 내 전체 아이템을 조회합니다.")
    @GetMapping("/items/{categoryName}")
    public Results categoryItems(@PathVariable(name = "categoryName") CategoryName categoryName) {
        List<ItemDto> items = itemService.findByCategoryName(categoryName)
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                , item.getItemImages().get(0).getItemImage().getFileName()
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLikeCount()
                                , item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());

        return Results.builder()
                .count(items.size())
                .items(items.stream().map(itemDto -> {return (Object)itemDto;}).collect(Collectors.toList())  )
                .build();
    }

    @ApiOperation(value = "카테고리+제조사 내 전체 아이템 조회", notes = "카테고리+제조사 내 전체 아이템을 조회합니다.")
    @GetMapping("/items/{categoryName}/{manufacturerName}")
    public Results categoryManufacturerItems(@PathVariable(name = "categoryName") CategoryName categoryName
            , @PathVariable(name = "manufacturerName") ManufacturerName manufacturerName) {
        List<ItemDto> items = itemService.findByCategoryAndManufacturer(categoryName,manufacturerName)
                .stream()
                .map(item ->
                        new ItemDto(item.getId()
                                , item.getItemImages().get(0).getItemImage().getFileName()
                                , item.getManufacturer().getManufacturerName(), item.getItemName()
                                , item.getLikeCount()
                                , item.getLatestPrice().getAmount()))
                .collect(Collectors.toList());

        return Results.builder()
                .count(items.size())
                .items(items.stream().map(itemDto -> {return (Object)itemDto;}).collect(Collectors.toList())  )
                .build();
    }

    @ApiOperation(value = "상품상세 조회", notes = "상품을 상세 조회합니다.")
    @GetMapping("/items/details/{itemId}")
    public Detail detailItem(@PathVariable("itemId") Long itemId) {
        NotebookDto dto = notebookQueryRepository.findById(itemId);
        List<String> names = itemImageService.findByItemId(itemId).stream().map(image -> image.getItemImage().getFileName()).collect(Collectors.toList());
            return Detail.builder()
                    .fileNames(names)
                    .detail(dto)
                    .build();
    }

    @ApiOperation(value = "상품정보 조회", notes = "상품 모델번호, 모델이름")
    @GetMapping("/items/simple/{itemId}")
    public SimpleItemDto simpleItemDto(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findByItemId(itemId);
        return SimpleItemDto.builder()
                .itemName(item.getItemName())
                .modelNumber(item.getModelNumber())
                .fileOriName(item.getItemImages().get(0).getItemImage().getFileName())
                .build();
    }

    @ApiOperation(value = "상품시세 조회", notes = "상품 시세를 조회합니다.")
    @GetMapping("/items/details/recent/{itemId}")
    public Results recentPrice(@PathVariable("itemId") Long itemId,
                               @RequestParam(name = "offset", defaultValue = "0") int offset, @RequestParam(name = "limit", defaultValue = "5") int limit) {
        PageRequest request = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<MarketPriceInfoDto> infos = dealQueryRepository.findRecentPrice(itemId, request);
        return Results.builder()
                .count(infos.size())
                .items(infos.stream().map(info -> {
                    return (Object) info;
                }).collect(Collectors.toList()))
                .build();
    }

    /**
     * DTO
     */
    @Data
    static class Results<T> {
        private int count;
        private List<T> items;

        @Builder
        public Results(int count, List<T> items) {
            this.count = count;
            this.items = items;
        }
    }

    @Data
    static class Detail<T> {
        private List<String> fileNames;
        private T detail;

        @Builder
        public Detail(List<String> fileNames, T detail) {
            this.fileNames = fileNames;
            this.detail = detail;
        }
    }

}
