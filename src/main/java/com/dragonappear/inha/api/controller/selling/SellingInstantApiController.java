package com.dragonappear.inha.api.controller.selling;


import com.dragonappear.inha.service.selling.SellingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"즉시판매 API"})
@RestController
@RequiredArgsConstructor
public class SellingInstantApiController {
    private final SellingService sellingService;

    /*@ApiOperation(value = "즉시판매 저장 API", notes = "즉시판매 저장")
    @PostMapping("/sellings/new/instant")
    public MessageDto createInstantSelling() {

    }*/
}
