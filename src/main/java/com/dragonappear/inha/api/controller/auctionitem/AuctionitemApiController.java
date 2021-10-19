package com.dragonappear.inha.api.controller.auctionitem;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.repository.auctionitem.AuctionitemRepository;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


@Api(tags = {"상품 조회 API"})
@RestController
@RequiredArgsConstructor
public class AuctionitemApiController {
    private final AuctionitemRepository auctionitemRepository;

    @ApiOperation(value = "경매상품 가격 조회", notes = "경매상품 가격 조회")
    @GetMapping("/auctionitems/{auctionitemId}")
    public Money auctionItemPrice(@PathVariable("auctionitemId") Long auctionitemId) {
        Auctionitem auctionitem = auctionitemRepository.findById(auctionitemId).orElse(null);
        return auctionitem.getPrice();
    }

    /*@ApiOperation(value = "해당상품 경매가격 전체 조회", notes = "거래소에 올라온 상품 경매가격 전체 조회")
    @GetMapping("/auctionitems/items/{itemsId}")
    public List<BigDecimal> auctionItemEntirePrice(@PathVariable("itemsId") Long itemsId) {

    }*/




}
