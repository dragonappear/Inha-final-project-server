package com.dragonappear.inha.web.admin.item;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.service.item.ItemImageService;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.web.admin.item.dto.ItemWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/web/admin/items")
public class ItemWebController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;

    @GetMapping
    public String getAllItems(Model model) {
        List<ItemWebDto> items = itemService.findAll().stream()
                .map(item -> {
                    return ItemWebDto.builder()
                            .itemId(item.getId())
                            .itemName(item.getItemName())
                            .itemImageName( item.getItemImages().size()==0 ? null : item.getItemImages().get(0).getItemImage().getFileName())
                            .build();
                })
                .collect(Collectors.toList());
        model.addAttribute("items", items);
        return "item/itemList";
    }
}
