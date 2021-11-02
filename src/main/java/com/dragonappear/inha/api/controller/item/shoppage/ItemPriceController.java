package com.dragonappear.inha.api.controller.item.shoppage;

import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.api.repository.deal.DealQueryRepository;
import com.dragonappear.inha.api.repository.deal.dto.MarketPriceInfoDto;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = {"아이템 가격 조회 API"})
@RestController
@RequiredArgsConstructor
public class ItemPriceController {
    private final DealQueryRepository dealQueryRepository;
    private final ItemService itemService;

    @ApiOperation(value = "아이템 시세 조회 API", notes = "아이템 시세를 조회")
    @GetMapping("/items/prices/recent/{itemId}")
    public ResultDto recentPrice(@PathVariable("itemId") Long itemId,
                                 @RequestParam(name = "offset", defaultValue = "0") int offset, @RequestParam(name = "limit", defaultValue = "5") int limit) {
        PageRequest request = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "createdDate"));
        return ResultDto.returnResults( dealQueryRepository.findRecentPrice(itemId, request));
    }
    
    @ApiOperation(value = "아이템 즉시 가격 조회 API", notes = "아이템 가격정보 조회")
    @GetMapping("/items/prices/instant/{itemId}")
    public Map<Object,Object> getInstantPrice(@PathVariable("itemId") Long itemId) {
        Map<Object, Object> map = new HashMap<>();
        map.put("즉시판매가", itemService.findInstantSellingPrice(itemId));
        map.put("즉시구매가", itemService.findInstantBuyingPrice(itemId));
        return map;
    }
}
