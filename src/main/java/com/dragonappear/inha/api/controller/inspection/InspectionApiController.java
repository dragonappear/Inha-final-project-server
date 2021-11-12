package com.dragonappear.inha.api.controller.inspection;


import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"검수 API"})
@RestController
@RequiredArgsConstructor
public class InspectionApiController {
    private final ItemService itemService;

}
