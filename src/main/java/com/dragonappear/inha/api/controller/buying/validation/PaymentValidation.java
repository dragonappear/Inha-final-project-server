package com.dragonappear.inha.api.controller.buying.validation;

import com.dragonappear.inha.api.controller.user.deal.dto.BidPaymentDto;
import com.dragonappear.inha.api.controller.user.deal.dto.PaymentDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.config.iamport.CancelDto;
import com.dragonappear.inha.config.iamport.IamportConfig;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;
import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.경매중;

@NoArgsConstructor
public class PaymentValidation {

    public static MessageDto validationPayment(PaymentDto dto,AuctionItemService auctionItemService, UserService userService, UserPointService userPointService, PaymentService paymentService) {
        try {
            validatePayment(dto
                    , userService.findOneById(dto.getBuyerId())
                    , auctionItemService.findById(dto.getAuctionitemId())
                    ,userPointService
                    ,paymentService);

        } catch (Exception e1) {
            try {
                if(IamportConfig.cancelPayment(CancelDto.getCancelDto(dto))==1){
                    return MessageDto.builder()
                            .message(getMessage("isValidateSuccess", false, "Status", e1.getMessage()+ " 결제를 취소하였습니다.")).build();
                }
                else{
                    return MessageDto.builder()
                            .message(getMessage("isValidateSuccess", false, "Status", e1.getMessage()+ " 결제취소가 완료되지 않았습니다.")).build();
                }
            } catch (Exception e2) {
                return MessageDto.builder()
                        .message(getMessage("isValidateSuccess", false, "Status",e1.getMessage()+ e2.getMessage())).build();
            }
        }
        return MessageDto.builder()
                .message(getMessage("isValidateSuccess", true, "Status","검증이 완료되었습니다.")).build();
    }

    // dto 검증
    public static void validatePayment(PaymentDto dto, User user, Auctionitem auctionitem,UserPointService userPointService,PaymentService paymentService) throws IllegalStateException{
        validateAddress(dto,user);
        validateImpIdAndMid(dto,paymentService);
        validatePoint(dto,user,userPointService);
        validateBuyer(dto, user, auctionitem);
        validatePrice(dto,auctionitem);
        validateAuctionitemStatus(dto,auctionitem);
        if (dto instanceof BidPaymentDto) {
            validateEndDate((BidPaymentDto) dto);
        }
    }

    // 결제주소 검증
    public static void validateAddress(PaymentDto dto, User user) throws IllegalStateException {
        boolean result = false;
        List<UserAddress> userAddresses = user.getUserAddresses();
        for (UserAddress userAddress : userAddresses) {
            if (userAddress.getId() == dto.getAddressId()) {
                result = true;
            }
        }
        if (!result) {
            throw new IllegalStateException("주소입력이 잘못되었습니다.");
        }
    }

    // 결제정보 검증
    public static void validateImpIdAndMid(PaymentDto dto,PaymentService paymentService) throws IllegalStateException{
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

    public static void validateAuctionitemStatus(PaymentDto dto, Auctionitem auctionitem) throws IllegalStateException{
        if(auctionitem.getAuctionitemStatus()!= 경매중){
            throw new IllegalStateException("해당 상품은 이미 판매되었습니다.");
        }
    }
    // 경매아이템 구매자 검증

    public static void validateBuyer(PaymentDto dto, User user, Auctionitem auctionitem) throws IllegalStateException{
        if (auctionitem.getSelling().getSeller().getId().equals(user.getId())) {
            throw new IllegalStateException("판매자의 상품을 판매자가 구매할 수 없습니다.");
        }
    }
    // 포인트 검증

    public static void validatePoint(PaymentDto dto, User user,UserPointService userPointService) throws IllegalStateException {
        Money amount = new Money(dto.getPoint());
        UserPoint point = userPointService.findLatestPoint(user.getId());
        if (amount.isLessThan(Money.wons(0L)) || point.getTotal().isLessThan(amount)) {
            throw new IllegalStateException("잘못된 포인트 입력입니다.");
        }
    }
    // 경매아이템 금액 검증

    public static void validatePrice(PaymentDto dto, Auctionitem auctionitem) throws IllegalStateException {
        BigDecimal price = auctionitem.getPrice().getAmount().setScale(0, RoundingMode.FLOOR);
        BigDecimal amount = dto.getPaymentPrice().add(dto.getPoint()).setScale(0, RoundingMode.FLOOR);
        if (!price.equals(amount)) {
            throw new IllegalStateException("잘못된 경매품 가격입니다.");
        }
    }
    // 입찰구매 기한일 검증
    private static void validateEndDate(BidPaymentDto dto) {
        if (!dto.getEndDate().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException("구매 입찰기간 입력이 잘못되었습니다.");
        }
    }
}
