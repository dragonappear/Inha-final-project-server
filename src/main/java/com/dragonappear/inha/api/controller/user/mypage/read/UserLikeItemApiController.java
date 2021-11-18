package com.dragonappear.inha.api.controller.user.mypage.read;

import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.api.repository.item.UserLikeItemQueryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;

@Api(tags = {"마이페이지 유저 관심상품 상세 조회 API"})
@RequiredArgsConstructor
@RestController
public class UserLikeItemApiController {
    private final UserLikeItemQueryRepository userLikeItemQueryRepository;

    @ApiOperation(value = "관심상품 상세내역 조회 API", notes = " 관심상품 상세내역 조회")
    @GetMapping("/api/v1/users/mypage/likeitems/{userId}")
    public ResultDto getUserLikeItemDto(@PathVariable("userId") Long userID) {
        return returnResults(userLikeItemQueryRepository.getMyPageUserLikeItemDtos(userID));
    }
}
