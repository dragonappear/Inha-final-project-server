package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.repository.selling.SellingRepository;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = {"아이템 경매상품 조회 API"})
@RestController
@RequiredArgsConstructor
public class AuctionitemApiController {
    private final AuctionItemService auctionItemService;
    private final SellingRepository sellingRepository;

    @ApiOperation(value = "경매상품 가격 조회", notes = "경매상품 가격 조회")
    @GetMapping("/auctionitems/{auctionitemId}")
    public Map<Object, Object> auctionItemPrice(@PathVariable("auctionitemId") Long auctionitemId) {
        return getResult( auctionItemService.findPriceById(auctionitemId));
    }

    @ApiOperation(value = "해당상품 최저경매가격 조회", notes = "경매소에 올라온 상품 최저가 조회")
    @GetMapping("/auctionitems/lowest/{itemsId}")
    public Map<Object, Object> auctionItemEntirePrice(@PathVariable("itemsId") Long itemsId) {
        return getResult(sellingRepository.findLowestSellingPrice(itemsId));
    }

    /**
     * DTO
     */
    public Map<Object, Object> getResult(Money money) {
        Map<Object, Object> map = new HashMap<>();
        if(money.getAmount().equals(BigDecimal.ZERO))
            map.put("amount", "경매상품의 가격이 조회되지않습니다");
        else{
            map.put("amount", money.getAmount());
        }
        return map;
    }
}
