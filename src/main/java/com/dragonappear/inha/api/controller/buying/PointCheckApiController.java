package com.dragonappear.inha.api.controller.buying;

import com.dragonappear.inha.service.user.UserPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(tags = {"구매전 유저 포인트 조회 API"})
@RestController
@RequiredArgsConstructor
public class PointCheckApiController {
    private final UserPointService userPointService;

    @ApiOperation(value = "유저 포인트 조회 API", notes = "유저 포인트 조회")
    @GetMapping("/payments/points/{userId}")
    public Result getUserPoint(@PathVariable("userId") Long userId) {
        return Result.builder()
                .total(userPointService.getTotal(userId).getAmount())
                .inspectionFee("무료")
                .deliveryFee("무료")
                .build();
    }

    @NoArgsConstructor
    @Data
    static class Result {
        private BigDecimal total;
        private String inspectionFee;
        private String deliveryFee;

        @Builder
        public Result(BigDecimal total, String inspectionFee, String deliveryFee) {
            this.total = total;
            this.inspectionFee = inspectionFee;
            this.deliveryFee = deliveryFee;
        }
    }
}

