package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.api.repository.buying.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.domain.buying.value.BuyingStatus.*;

@Api(tags = {"마이페이지 유저 구매내역 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class ReadDetailUserBuyingApiController {
    private final BuyingQueryRepository buyingQueryRepository;

    @ApiOperation(value = "유저 구매입찰 상세 내역 API", notes = "아이템 URL,구매희망가,상품이름,만료일")
    @GetMapping("/users/mypage/buying/bid/{userId}")
    public Results getUserBidItems(@PathVariable("userId") Long userId) {
        return getResults(buyingQueryRepository.getMyPageUserBuyingBidDto(userId));
    }

    @ApiOperation(value = "유저 구매진행중 상세 내역 API", notes = "거래아이디,아이템 URL,상품이름,상태")
    @GetMapping("/users/mypage/buying/ongoing/{userId}")
    public Results getUserOngoingItems(@PathVariable("userId") Long userId) {
        return getResults(buyingQueryRepository.getMyPageUserBuyingOngoingDto(userId));
    }

    @ApiOperation(value = "유저 구매완료 상세 내역 API", notes = "거래아이디, 아이템 URL,상품이름,상태")
    @GetMapping("/users/mypage/buying/end/{userId}")
    public Results getUserEndItems(@PathVariable("userId") Long userId) {
        return getResults(buyingQueryRepository.getMyPageUserBuyingEndDto(userId));
    }

    /**
     * DTO
     */
    @Data
    static class Results<T>{
        private int count;
        private List<T> items;

        @Builder
        public Results(int count, List<T> items) {
            this.count = count;
            this.items = items;
        }
    }

    private <K> Results getResults (List<K> dtos) {
        return Results.builder()
                .count(dtos.size())
                .items( dtos.stream().map(dto -> (Object) dto).collect(Collectors.toList()))
                .build();
    }
}
