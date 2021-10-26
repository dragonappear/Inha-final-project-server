package com.dragonappear.inha.api.controller.user.mypage.create;

import com.dragonappear.inha.api.returndto.MessageDto;
import com.dragonappear.inha.domain.value.Address;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.dragonappear.inha.api.returndto.MessageDto.getMessage;

@Api(tags = {"마이페이지 유저 정보 생성 API"})
@RequiredArgsConstructor
@RestController
public class CreateUserInfoController {
    private final UserAddressService userAddressService;
    private final UserService userService;

    @ApiOperation(value = "유저 주소 저장 API", notes = "유저 주소 저장")
    @PostMapping("/users/update/addresses/{userId}")
    public Map<String, Object> createUserAddress(@PathVariable("userId") Long userId, @RequestBody Address userAddress) {
        try{
            userAddressService.save(userId, userAddress);
            return getMessage("isInserted", true, "Status", "주소가 등록되었습니다");
        }catch (Exception e){
            return getMessage("isInserted", false, "Status", e.getMessage());
        }
    }
}
