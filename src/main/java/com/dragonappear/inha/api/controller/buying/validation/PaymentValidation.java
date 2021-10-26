package com.dragonappear.inha.api.controller.buying.validation;

import com.dragonappear.inha.api.controller.buying.dto.PaymentDto;
import com.dragonappear.inha.config.iamport.CancelDto;
import com.dragonappear.inha.config.iamport.IamportConfig;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.경매중;

@NoArgsConstructor
public class PaymentValidation {
    private AuctionItemService auctionItemService;
    private UserService userService;
    private UserPointService userPointService;

    @Builder
    public PaymentValidation(AuctionItemService auctionItemService, UserService userService, UserPointService userPointService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
        this.userPointService = userPointService;
    }

    // 경매아이템 상태 검증
    public void validateAuctionitemStatus(PaymentDto dto) throws IllegalStateException{
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        if(auctionitem.getAuctionitemStatus()!= 경매중){
            throw new IllegalStateException("해당 상품은 이미 판매되었습니다.");
        }
    }
    // 포인트 검증
    public void validatePoint(PaymentDto dto) throws IllegalStateException {
        User user = userService.findOneById(dto.getBuyerId());
        Money amount = new Money(dto.getPoint());
        UserPoint point = userPointService.findLatestPoint(user.getId());
        if (amount.isLessThan(Money.wons(0L)) || point.getTotal().isLessThan(amount)) {
            throw new IllegalStateException("잘못된 포인트 입력입니다.");
        }
    }

    // 경매아이템 금액 검증
    public void validatePrice(PaymentDto dto) throws IllegalStateException {
        Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
        Money price = auctionitem.getPrice();
        Money validate = new Money(dto.getPaymentPrice()).plus(new Money(dto.getPoint()));
        if (!price.equals(validate)) {
            throw new IllegalStateException("잘못된 경매품 가격입니다.");
        }
    }
}
