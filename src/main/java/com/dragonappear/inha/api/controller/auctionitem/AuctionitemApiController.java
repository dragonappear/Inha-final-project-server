package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.api.repository.auctionitem.AuctionitemApiRepository;
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


@Api(tags = {"경매아이템 조회 API"})
@RestController
@RequiredArgsConstructor
public class AuctionitemApiController {
    private final AuctionItemService auctionItemService;
    private final SellingRepository sellingRepository;
    private final AuctionitemApiRepository auctionitemApiRepository;

    @ApiOperation(value = "경매아이템아이디로 가격 조회", notes = "경매아이템아이디로 가격 조회")
    @GetMapping("/auctionitems/{auctionitemId}")
    public Map<Object, Object> auctionitemPrice(@PathVariable("auctionitemId") Long auctionitemId) {
        return getResult( auctionItemService.findPriceById(auctionitemId));
    }

    @ApiOperation(value = "경매아이템아이디로 최저판매입찰금 조회", notes = "경매아이템아이디로 최저경매가격 조회")
    @GetMapping("/auctionitems/lowest/{itemId}")
    public Map<Object, Object> auctionitemLowestPrice(@PathVariable("itemId") Long itemId) {
        return sellingRepository.findLowestSellingPrice(itemId);
    }

    @ApiOperation(value = "아이템아이디로 판매입찰금 모두 조회", notes = "아이템 판매입찰금 모두 조회")
    @GetMapping("/auctionitems/all/{itemId}")
    public Map<Object, Object> auctionItemEntirePrice(@PathVariable("itemId") Long itemId) {
        return auctionitemApiRepository.findBidAuctionitems(itemId);
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
