package com.dragonappear.inha.web.admin.item;

import com.dragonappear.inha.repository.item.CategoryRepository;
import com.dragonappear.inha.service.item.ItemService;
import com.dragonappear.inha.web.admin.item.dto.ItemWebDto;
import com.dragonappear.inha.web.repository.DealWebRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/web/admin/items")
public class ItemWebController {
    private final ItemService itemService;
    private final DealWebRepository dealWebRepository;
    private final CategoryRepository categoryRepository;


    @GetMapping
    public String getAllItems(Model model) {
        List<String> categories = categoryRepository.findAll()
                .stream()
                .map(category -> {
                    return category.getCategoryName().toString();
                }).collect(Collectors.toList());
        List<String> types = new ArrayList<>(Arrays.asList("monitor", "notebook", "smart_phone", "tablet", "keyboard"));
        List<ItemWebDto> items = itemService.findAll().stream()
                .map(item -> {
                    int itemDealCount = dealWebRepository.getItemDealCount(item.getId());
                    String category = "";
                    for (String type : types) {
                        if (item.getClass().getAnnotation(DiscriminatorValue.class).value().equals(type)) {
                            category = type;
                        }
                    }
                    return ItemWebDto.builder()
                            .itemId(item.getId())
                            .category(category)
                            .manufacturer(item.getManufacturer().getManufacturerName().toString())
                            .itemName(item.getItemName())
                            .itemDealCount(itemDealCount)
                            .build();
                })
                .collect(Collectors.toList());
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "item/itemList";
    }
}
