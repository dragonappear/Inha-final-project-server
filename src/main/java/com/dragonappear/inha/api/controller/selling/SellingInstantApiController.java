package com.dragonappear.inha.api.controller.selling;


import com.dragonappear.inha.api.controller.selling.dto.SellingDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.InstantAuctionitem;
import com.dragonappear.inha.domain.buying.Buying;
import com.dragonappear.inha.domain.deal.Deal;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.service.selling.SellingService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"즉시판매 API"})
@RestController
@RequiredArgsConstructor
public class SellingInstantApiController {
    private final SellingService sellingService;
    private final ItemService itemService;
    private final UserService userService;
    private final BuyingService buyingService;
    private final DealService dealService;

    @ApiOperation(value = "즉시판매 저장 API", notes = "즉시판매 저장")
    @PostMapping("/sellings/new/instant")
    public MessageDto createInstantSelling(@RequestBody SellingDto dto) {
        User user = userService.findOneById(dto.getUserId());
        Item item = itemService.findByItemId(dto.getItemId());
        Buying buying = buyingService.findById(dto.getBuyingId());
        Auctionitem auctionitem = new InstantAuctionitem(item, new Money(dto.getPrice()));
        Long save = sellingService.save(user, auctionitem);

        Selling selling = sellingService.findBySellingId(save);
        Long save1 = dealService.save(new Deal(buying, selling));

        return MessageDto.builder()
                .message(getMessage("isPaySuccess", true, "Status", "결제가 완료되었습니다."))
                .build();

    }
}
