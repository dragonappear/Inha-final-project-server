package com.dragonappear.inha.api.controller.user.mypage.read;


import com.dragonappear.inha.api.repository.seliing.SellingQueryRepository;
import com.dragonappear.inha.domain.deal.value.DealStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"마이페이지 유저 판매내역 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class UserSellingApiController {
    private final SellingQueryRepository sellingQueryRepository;

    @ApiOperation(value = "유저 판매입찰 상세 내역 API", notes = "리턴값 : imageUrl,itemName, amount,endDate")
    @GetMapping("/users/mypage/selling/bid/{userId}")
    public Results getMyPageUserSellingBidDto(@PathVariable("userId") Long userId) {
        return returnResults(sellingQueryRepository.getMyPageUserSellingBidDto(userId));
    }

    @ApiOperation(value = "유저 판매진행 상세 내역 API", notes = "리턴값: dealId ,imageUrl,itemName,dealStatus")
    @GetMapping("/users/mypage/selling/ongoing/{userId}")
    public Results getMyPageUserSellingOngoingDto(@PathVariable("userId") Long userId) {
        return returnResults(sellingQueryRepository.getMyPageUserSellingOngoingDto(userId));
    }

    @ApiOperation(value = "유저 판매종료 상세 내역 API", notes = " 리턴값 : sellingId, imageUrl ,itemName, purchaseTime ,sellingStatus")
    @GetMapping("/users/mypage/selling/end/{userId}")
    public Results getMyPageUserSellingEndDto(@PathVariable("userId") Long userId) {
        return returnResults(sellingQueryRepository.getMyPageUserSellingEndDto(userId));
    }

    /**
     * DTO
     */
    @Data
    static class Results<T> {
        private int count;
        private List<T> items;
        @Builder
        public Results(int count, List<T> items) {
            this.count = count;
            this.items = items;
        }
    }

    public<T> Results returnResults(List<T> dtos) {
        return Results.builder()
                .count(dtos.size())
                .items(dtos.stream().map(dto -> (Object) dto).collect(Collectors.toList()))
                .build();
    }
}
