package com.dragonappear.inha.api.controller.item.shoppage;

import com.dragonappear.inha.api.repository.deal.DealQueryRepository;
import com.dragonappear.inha.api.repository.deal.dto.MarketPriceInfoDto;
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
import java.math.BigDecimal;
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

    @ApiOperation(value = "아이템 시세 조회", notes = "아이템 시세를 조회합니다.")
    @GetMapping("/items/prices/recent/{itemId}")
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


    @ApiOperation(value = "아이템 즉시 가격 조회", notes = "아이템 가격정보 조회")
    @GetMapping("/items/prices/instant/{itemId}")
    public Map<String,BigDecimal> getInstantPrice(@PathVariable("itemId") Long itemId) {
        Map<String, BigDecimal> map = new HashMap<>();
        BigDecimal instantSellingPrice = itemService.findInstantSellingPrice(itemId).getAmount();
        BigDecimal instantBuyingPrice = itemService.findInstantBuyingPrice(itemId).getAmount();
        map.put("즉시구매가", instantBuyingPrice);
        map.put("즉시판매가", instantSellingPrice);
        return map;
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
