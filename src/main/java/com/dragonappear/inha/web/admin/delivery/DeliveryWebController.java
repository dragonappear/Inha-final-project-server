package com.dragonappear.inha.web.admin.delivery;

import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.web.repository.DealWebRepository;
import com.dragonappear.inha.web.repository.dto.ReturnDealWebDto;
import com.dragonappear.inha.web.repository.dto.SendDealWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/web/admin/deliveries")
public class DeliveryWebController {
    private final FcmSendService fcmSendService;
    private final DealWebRepository dealWebRepository;

    @GetMapping("/sends")
    public String sendList(Model model) {
        List<SendDealWebDto> sendList = dealWebRepository.getSendList();
        model.addAttribute("list", sendList);
        return "delivery/sendList";
    }

    @PostMapping("/sends")
    public String sendRegister() {
        return "delivery/sendRegister";
    }

    @GetMapping("/returns")
    public String returnList(Model model) {
        List<ReturnDealWebDto> returnList = dealWebRepository.getReturnList();
        model.addAttribute("list", returnList);
        return "delivery/returnList";
    }

    @PostMapping("/returns")
    public String returnRegister() {
        return "delivery/returnRegister";
    }

}
