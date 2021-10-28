package com.dragonappear.inha.api;

import com.dragonappear.inha.api.controller.buying.dto.InstantPaymentDto;
import com.dragonappear.inha.api.controller.buying.dto.PaymentDto;
import com.dragonappear.inha.api.controller.user.deal.dto.AddressDto;
import com.dragonappear.inha.api.controller.user.deal.dto.PointDto;
import com.dragonappear.inha.api.controller.user.mypage.dto.UserAccountApiDto;
import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.api.controller.buying.iamport.IamportController;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.exception.deal.DealException;
import com.dragonappear.inha.exception.buying.PaymentException;
import com.dragonappear.inha.exception.user.*;
import com.dragonappear.inha.service.buying.BuyingService;
import com.dragonappear.inha.service.deal.DealService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.selling.SellingService;
import com.dragonappear.inha.service.user.UserPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.math.BigDecimal;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class ExceptionController {
    private final UserPointService userPointService;
    private final SellingService sellingService;
    private final DealService dealService;
    private final BuyingService buyingService;
    private final PaymentService paymentService;

    //400
    @ExceptionHandler({IllegalArgumentException.class,IllegalArgumentException.class})
    public ResponseEntity<Object> BadRequestException(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // 401
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 거래생성 예외처리
    @ExceptionHandler({PaymentException.class})
    public MessageDto paymentException(final PaymentException e) {
        try{
            IamportController.cancelPayment(e.getCancelDto());
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", e.getMessage() + "결제를 취소하였습니다.")).build();
        }catch (Exception e1){
            return MessageDto.builder()
                    .message(getMessage("isCancelSuccess", false, "Status", e.getMessage() + "결제취소가 완료되지 않았습니다.")).build();
        }
    }

    // 결제생성 예외처리
    @ExceptionHandler({DealException.class})
    public MessageDto dealException(final DealException e) {
        try{
            PaymentDto paymentDto = e.getPaymentDto();
            if ( e.getPaymentDto().getPoint().equals(BigDecimal.ZERO)) {
                userPointService.accumulate(paymentDto.getBuyerId(), new Money(paymentDto.getPoint())); //포인트 재적립
            }
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", e.getMessage() + "결제를 취소하였습니다.")).build();
        }catch (Exception e1) {
            return MessageDto.builder()
                    .message(getMessage("isPaySuccess", false, "Status", e.getMessage() +"결제취소가 완료되지 않았습니다.")).build();
        }
    }

    @ExceptionHandler({IOException.class})
    public MessageDto ioException(final IOException e) {
        return MessageDto.builder()
                .message(getMessage("isFoundSuccess", false, "Status", "경로에 없는 파일입니다.")).build();
    }

    @ExceptionHandler({NotFoundUserIdException.class, NotFoundUserEmailException.class})
    public MessageDto notFoundUserException(final RuntimeException e) {
        return MessageDto.builder()
                .message(getMessage("isRegistered", false, "id", null,"role",e.getMessage()))
                .build();
    }

    @ExceptionHandler({NotFoundUserAddressListException.class})
    public AddressDto notFoundUserAddressListException(final NotFoundUserAddressListException e) {
        return AddressDto.builder()
                .addressId(null)
                .address(null)
                .build();
    }

    @ExceptionHandler({NotFoundUserAddressException.class})
    public AddressDto notFoundUserAddressException(final NotFoundUserAddressException e) {
        return AddressDto.builder()
                .addressId(null)
                .address(null)
                .build();
    }

    @ExceptionHandler({NotFoundUserAccountException.class})
    public UserAccountApiDto notFoundUserAccountException(final NotFoundUserAccountException e) {
        return UserAccountApiDto.builder()
                .account(null)
                .build();
    }

    @ExceptionHandler({NotFoundUserPointListException.class})
    public PointDto notFoundUserAccountException(final NotFoundUserPointListException e) {
        return PointDto.builder()
                .total(null)
                .inspectionFee(null)
                .deliveryFee(null)
                .build();
    }
}
