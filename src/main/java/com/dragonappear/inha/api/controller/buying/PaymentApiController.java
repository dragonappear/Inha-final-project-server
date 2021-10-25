package com.dragonappear.inha.api.controller.buying;


import com.dragonappear.inha.api.controller.user.mypage.update.UpdateUserApiController;
import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.domain.value.Money;
import com.dragonappear.inha.service.auctionitem.AuctionItemService;
import com.dragonappear.inha.service.payment.PaymentService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus.*;

@Api(tags = {"구매 결제 정보 저장 API"})
@RestController
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService paymentService;
    private final AuctionItemService auctionItemService;
    private final UserService userService;

    @ApiOperation(value = "결제 정보 저장", notes = "결제 정보 저장하기")
    @PostMapping("/payments/new")
    public Result savePayment(@RequestBody PaymentDto dto) {
        try {
            Auctionitem auctionitem = auctionItemService.findById(dto.getAuctionitemId());
            User user = userService.findOneById(dto.getBuyerId());
            if(auctionitem.getAuctionitemStatus()!= 경매중){
                throw new IllegalStateException("해당 경매상품은 이미 판매되었습니다");
            }
            paymentService.save(
                    dto.toEntity(user, auctionitem)
            );
        } catch (Exception e) {
            // 결제취소 로직 포함되어있어야 한다.
            return Result.builder()
                    .result(putResult("isPaySuccess", false, "Status", e.getMessage()))
                    .build();
        }

        return Result.builder()
                .result(putResult("isPaySuccess", true, "Status", "결제가 성공하였습니다."))
                .build();
    }

    /**
     * DTO
     */

    @NoArgsConstructor
    @Data
    static class PaymentDto {
        private String pgName;
        private String impId;
        private String merchantId;
        private BigDecimal paymentPrice;
        private Long buyerId;
        private Long auctionitemId;

        @Builder
        public PaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice, Long buyerId, Long auctionitemId) {
            this.pgName = pgName;
            this.impId = impId;
            this.merchantId = merchantId;
            this.paymentPrice = paymentPrice;
            this.buyerId = buyerId;
            this.auctionitemId = auctionitemId;
        }

        Payment toEntity(User user, Auctionitem auctionitem) {
            return Payment.builder()
                    .paymentPrice(new Money(this.getPaymentPrice()))
                    .user(user)
                    .auctionitem(auctionitem)
                    .impId(this.getImpId())
                    .merchantId(this.merchantId)
                    .pgName(this.pgName)
                    .build();
        }
    }


    @Data
    static class Result {
        private Map<String, Object> result;
        @Builder
        public Result(Map<String, Object> result) {
            this.result = result;
        }
    }

    public Map<String, Object> putResult(String insert, Boolean bool, String status, String content) {
        Map<String, Object> result = new HashMap<>();
        result.put(insert, bool);
        result.put(status, content);
        return result;
    }
}
