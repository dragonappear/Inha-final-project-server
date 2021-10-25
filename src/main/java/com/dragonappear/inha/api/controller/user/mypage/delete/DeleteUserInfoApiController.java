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

@Api(tags = {"마이페이지 유저 정보 삭제 API"})
@RequiredArgsConstructor
@RestController
public class DeleteUserInfoApiController {
    private final UserAddressService userAddressService;

    @ApiOperation(value = "유저 주소 삭제 API", notes = "유저 주소를 삭제합니다.")
    @DeleteMapping(value = "users/delete/addresses/{userId}")
    public Address deleteUserAddress(@PathVariable("userId") Long userId, @RequestBody Address address) {
        userAddressService.deleteAddress(userId, address);
        return address;
    }
}