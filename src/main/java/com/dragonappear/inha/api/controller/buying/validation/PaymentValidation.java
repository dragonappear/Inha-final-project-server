package com.dragonappear.inha.api.controller.buying.validation;

import com.dragonappear.inha.api.controller.buying.dto.PaymentDto;
import com.dragonappear.inha.config.iamport.CancelDto;
import com.dragonappear.inha.config.iamport.IamportConfig;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.경매중;

@NoArgsConstructor
public class PaymentValidation {
    private AuctionItemService auctionItemService;
    private UserService userService;
    private UserPointService userPointService;
    private PaymentService paymentService;

    @Builder
    public PaymentValidation(AuctionItemService auctionItemService, UserService userService, UserPointService userPointService, PaymentService paymentService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
        this.userPointService = userPointService;
        this.paymentService = paymentService;
    }

    // 결제정보 검증
    public void validateImpIdAndMid(PaymentDto dto) throws IllegalStateException{
        List<Payment> all = paymentService.findAll();
        for (Payment payment : all) {
            if (payment.getMerchantId() == dto.getMerchantId()) {
                throw new IllegalStateException("중복된 merchantId가 입력되었습니다.");
            }
            if (payment.getImpId().equals(dto.getImpId())) {
                throw new IllegalStateException("중복된 impId가 입력되었습니다.");
            }
        }
    }

    // 경매아이템 상태 검증
    public void validateAuctionitemStatus(PaymentDto dto,Auctionitem auctionitem) throws IllegalStateException{
        if(auctionitem.getAuctionitemStatus()!= 경매중){
            throw new IllegalStateException("해당 상품은 이미 판매되었습니다.");
        }
    }

    // 경매아이템 구매자 검증
    public void validateBuyer(PaymentDto dto,User user,Auctionitem auctionitem) throws IllegalStateException{
        if (auctionitem.getSelling().getSeller().getId().equals(user.getId())) {
            throw new IllegalStateException("판매자의 상품을 판매자가 구매할 수 없습니다.");
        }
    }

    // 포인트 검증
    public void validatePoint(PaymentDto dto,User user) throws IllegalStateException {
        Money amount = new Money(dto.getPoint());
        UserPoint point = userPointService.findLatestPoint(user.getId());
        if (amount.isLessThan(Money.wons(0L)) || point.getTotal().isLessThan(amount)) {
            throw new IllegalStateException("잘못된 포인트 입력입니다.");
        }
    }

    // 경매아이템 금액 검증
    public void validatePrice(PaymentDto dto,Auctionitem auctionitem) throws IllegalStateException {
        BigDecimal price = auctionitem.getPrice().getAmount().setScale(0, RoundingMode.FLOOR);
        BigDecimal amount = dto.getPaymentPrice().add(dto.getPoint()).setScale(0, RoundingMode.FLOOR);
        if (!price.equals(amount)) {
            throw new IllegalStateException("잘못된 경매품 가격입니다.");
        }
    }
}
