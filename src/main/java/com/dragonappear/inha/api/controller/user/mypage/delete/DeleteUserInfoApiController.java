package com.dragonappear.inha.api.controller.user.mypage.delete;

import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.service.user.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"마이페이지 유저 정보 삭제 API"})
@RequiredArgsConstructor
@RestController
public class DeleteUserInfoApiController {
    private final UserAddressService userAddressService;

    @ApiOperation(value = "유저 주소 삭제 API", notes = "유저 주소 삭제")
    @DeleteMapping(value = "/api/v1/users/delete/addresses/{userId}")
    public Map<String, Object> deleteUserAddress(@PathVariable("userId") Long userId, @RequestBody Address address) {
        userAddressService.deleteAddress(userId, address);
        return getMessage("isDeleted", true, "Status", "주소가 삭제되었습니다.");
    }
}
