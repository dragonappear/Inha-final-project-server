package com.dragonappear.inha.web.admin.item;

import com.dragonappear.inha.domain.item.value.ManufacturerName;
import com.dragonappear.inha.repository.item.CategoryManufacturerRepository;
import com.dragonappear.inha.repository.item.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dragonappear.inha.domain.item.value.CategoryName.*;
import static com.dragonappear.inha.domain.item.value.CategoryName.모니터;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/web/admin/items/register")
public class ItemWebRegisterController {
    private final CategoryManufacturerRepository categoryManufacturerRepository;
    private final CategoryRepository categoryRepository;
    private Map<String, String> urlMap = new HashMap<>();


    @PostConstruct
    public void init() {
        urlMap.put("노트북", "redirect:/web/admin/items/register/notebooks");
        urlMap.put("태블릿", "redirect:/web/admin/items/register/tablets");
        urlMap.put("모니터", "redirect:/web/admin/items/register/monitors");
        urlMap.put("스마트폰", "redirect:/web/admin/items/register/smartphones");
    }

    @PostMapping
    public String itemRegister(String categoryName) {
        return urlMap.get(categoryName);
    }

    // 노트북 등록 페이지
    @GetMapping("/notebooks")
    public String notebookRegister(Model model) {
        List<ManufacturerName> names = categoryManufacturerRepository.findByCategoryName(노트북);
        model.addAttribute("names", names);
        return "item/product/notebookRegister";
    }

    // 태블릿 등록 페이지
    @GetMapping("/tablets")
    public String tabletRegister(Model model) {
        List<ManufacturerName> names = categoryManufacturerRepository.findByCategoryName(태블릿);
        model.addAttribute("names", names);
        return "item/product/tabletRegister";
    }

    // 스마트폰 등록 페이지
    @GetMapping("/smartphones")
    public String smartPhoneRegister(Model model) {
        List<ManufacturerName> names = categoryManufacturerRepository.findByCategoryName(스마트폰);
        model.addAttribute("names", names);
        return "item/product/smartPhoneRegister";
    }

    // 모니터 등록 페이지
    @GetMapping("/monitors")
    public String monitorRegister(Model model) {
        List<ManufacturerName> names = categoryManufacturerRepository.findByCategoryName(모니터);
        model.addAttribute("names", names);
        return "item/product/monitorRegister";
    }

}
