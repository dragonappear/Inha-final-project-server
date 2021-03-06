package com.dragonappear.inha.api.service.buying;

import com.dragonappear.inha.api.controller.buying.dto.BidPaymentApiDto;
import com.dragonappear.inha.api.controller.buying.dto.InstantPaymentApiDto;
import com.dragonappear.inha.api.controller.buying.dto.PaymentApiDto;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.value.SellingStatus;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.user.UserPoint;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.buying.PaymentException;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.selling.SellingService;
import com.dragonappear.inha.service.user.UserPointService;
import com.dragonappear.inha.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ValidatePaymentService {
    private final ItemService itemService;
    private final UserService userService;
    private final UserPointService userPointService;
    private final PaymentService paymentService;
    private final SellingService sellingService;

    public void validateInstantPayment(InstantPaymentApiDto dto) {
        try {
            User user = userService.findOneById(dto.getBuyerId());
            Selling selling = sellingService.findBySellingId(dto.getSellingId());
            validateSelling(selling);
            Auctionitem auctionitem = selling.getAuctionitem();
            validateBuyer(dto, user, auctionitem);
            validatePrice(dto,auctionitem);
            validateAddress(dto,user);
            validateImpIdAndMid(dto,paymentService);
            validatePoint(dto,user,userPointService);
        } catch (Exception e) {
            throw new PaymentException(e.getMessage(), CancelDto.getCancelDto(dto));
        }

    }

    public void validateBidPayment(BidPaymentApiDto dto) {
        try {
            User user = userService.findOneById(dto.getBuyerId());
            Item item = itemService.findByItemId(dto.getItemId());
            validateAddress(dto,user);
            validateImpIdAndMid(dto,paymentService);
            validatePoint(dto,user,userPointService);
            validateEndDate(dto);
        } catch (Exception e) {
            throw new PaymentException(e.getMessage(), CancelDto.getCancelDto(dto));
        }
    }

    /**
     *
     * ?????? ??????
     *
     */

    // ?????? ??????
    private void validateSelling(Selling selling) throws PaymentException {
        if (selling.getSellingStatus() != SellingStatus.???????????????) {
            throw new PaymentException("?????? ????????? ?????? ?????????????????????.");
        }
    }

    // ???????????? ??????
    public void validateAddress(PaymentApiDto dto, User user) throws PaymentException {
        boolean result = false;
        List<UserAddress> userAddresses = user.getUserAddresses();
        for (UserAddress userAddress : userAddresses) {
            if (userAddress.getId() == dto.getAddressId()) {
                result = true;
            }
        }
        if (!result) {
            throw new PaymentException("??????????????? ?????????????????????.");
        }
    }

    // ???????????? ??????
    public void validateImpIdAndMid(PaymentApiDto dto, PaymentService paymentService) throws PaymentException {
        paymentService.findAll().stream()
                .forEach(payment -> {
                    if (payment.getMerchantId() == dto.getMerchantId()) {
                        throw new PaymentException("????????? merchantId??? ?????????????????????.");
                    }
                    if (payment.getImpId().equals(dto.getImpId())) {
                        throw new PaymentException("????????? impId??? ?????????????????????.");
                    }
                });
    }

    // ??????????????? ????????? ??????
    public void validateBuyer(PaymentApiDto dto, User user, Auctionitem auctionitem) throws PaymentException {
        if (auctionitem.getSelling().getSeller().getId().equals(user.getId())) {
            throw new PaymentException("???????????? ????????? ???????????? ????????? ??? ????????????.");
        }
    }
    // ????????? ??????

    public void validatePoint(PaymentApiDto dto, User user, UserPointService userPointService) throws PaymentException {
        Money amount = new Money(dto.getPoint());
        UserPoint point = userPointService.findLatestPoint(user.getId());
        if (amount.isLessThan(Money.wons(0L)) || point.getTotal().isLessThan(amount)) {
            throw new PaymentException("????????? ????????? ???????????????.");
        }
    }
    // ??????????????? ?????? ??????

    public void validatePrice(PaymentApiDto dto, Auctionitem auctionitem) throws PaymentException {
        BigDecimal price = auctionitem.getPrice().getAmount().setScale(0, RoundingMode.FLOOR);
        BigDecimal amount = dto.getPaymentPrice().add(dto.getPoint()).setScale(0, RoundingMode.FLOOR);
        if (!price.equals(amount)) {
            throw new PaymentException("????????? ????????? ???????????????.");
        }
    }

    // ???????????? ????????? ??????
    private void validateEndDate(BidPaymentApiDto dto)  throws PaymentException {
        if (!dto.getEndDate().isAfter(LocalDateTime.now())) {
            throw new PaymentException("?????? ???????????? ????????? ?????????????????????.");
        }
    }
}
