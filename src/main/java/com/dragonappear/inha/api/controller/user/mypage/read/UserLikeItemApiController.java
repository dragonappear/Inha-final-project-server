package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.api.repository.item.UserLikeItemQueryRepository;
import com.dragonappear.inha.domain.item.value.ManufacturerName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"마이페이지 유저 관심상품 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class UserLikeItemApiController {
    private final UserLikeItemQueryRepository userLikeItemQueryRepository;

    @ApiOperation(value = "관심상품 상세내역 조회", notes = " 리턴값: itemUrl,manufacturerName,itemName,latestPrice")
    @GetMapping("/users/mypage/likeitems/{userId}")
    public Results getUserLikeItemDto(@PathVariable("userId") Long userID) {
        return getResults(userLikeItemQueryRepository.getMyPageUserLikeItemDtos(userID));
    }

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

    public <T> Results getResults(List<T> dtos) {
        return Results.builder()
                .count(dtos.size())
                .items(dtos.stream().map(dto -> (Object) dto).collect(Collectors.toList()))
                .build();
    }
}
