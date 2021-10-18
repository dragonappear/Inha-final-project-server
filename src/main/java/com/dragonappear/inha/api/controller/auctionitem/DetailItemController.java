package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.dragonappear.inha.api.repository.DealQueryRepository;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"상품상세 조회 API"})
@RestController
@RequiredArgsConstructor
public class DetailItemController {
    private final ItemService itemService;
    private final DealQueryRepository dealQueryRepository;

    @ApiOperation(value = "상품상세 조회", notes = "상품을 상세 조회합니다.")
    @GetMapping("/api/v1/items/detail/{itemId}")
    public DetailItemDto detailItem(@PathVariable("itemId") Long itemId) {
        Item find = itemService.findByItemId(itemId);
        return new DetailItemDto(itemId
                , find.getItemImages().stream().map(image -> image.getItemImage().getFileOriName()).collect(Collectors.toList())
                , find.getManufacturer().getManufacturerName()
                , find.getItemName()
                , find.getModelNumber()
                , find.getReleaseDay()
                , find.getColor()
                , find.getLikeCount()
                , find.getLatestPrice().getAmount()
        );
    }

    @ApiOperation(value = "상품시세 조회", notes = "상품 시세를 조회합니다.")
    @GetMapping("/api/v1/items/detail/recent/{itemId}")
    public List<BigDecimal> recentPrice(@PathVariable("itemId") Long itemId) {
        PageRequest request = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        return dealQueryRepository.findRecentPrice(itemId, request);
    }

}
