package com.dragonappear.inha.api.controller.inspection;

import com.dragonappear.inha.domain.inspection.InspectionManual;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.item.Notebook;
import com.dragonappear.inha.service.item.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"검수 API"})
@RestController
@RequiredArgsConstructor
public class InspectionApiController {
    private final ItemService itemService;

    @ApiOperation(value = "검수기준 조회 API by 아이템아이디로", notes = "검수 조회")
    @GetMapping("/inspections/manual/{itemId}")
    public String getInspectionCriteria(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findByItemId(itemId);
        if (item instanceof Notebook) {
            return InspectionManual.notebook;
        }

        return null;
    }
}
