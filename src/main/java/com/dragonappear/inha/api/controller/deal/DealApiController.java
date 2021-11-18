package com.dragonappear.inha.api.controller.deal;

import com.dragonappear.inha.api.controller.deal.dto.DealApiDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.service.deal.DealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"거래 API"})
@RestController
@RequiredArgsConstructor
public class DealApiController {
    private final DealService dealService;

    @ApiOperation(value = "거래 조회 API by 거래아이디로", notes = "거래 조회")
    @GetMapping("/api/v1/deals/{dealId}")
    public MessageDto getDealStatus(@PathVariable("dealId") Long dealId) {
        Deal deal = dealService.findById(dealId);
        DealApiDto dto = DealApiDto
                .builder()
                .createDate(deal.getCreatedDate())
                .buyingId(deal.getBuying().getId())
                .sellingId(deal.getSelling().getId())
                .itemName(deal.getSelling().getAuctionitem().getItem().getItemName())
                .itemImageFileName(deal.getSelling().getAuctionitem().getItem().getItemImages().get(0).getItemImage().getFileOriName())
                .price(deal.getSelling().getAuctionitem().getPrice().getAmount())
                .build();
        return MessageDto.builder()
                .message(getMessage("거래정보",dto))
                .build();
    }
}
