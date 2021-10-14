package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.api.controller.auctionitem.dto.DetailItemDto;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DetailItemController {
    private final ItemService itemService;
    private final DealService dealService;

    @GetMapping("/items/{itemId}")
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
}
