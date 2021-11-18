package com.dragonappear.inha.web.admin.selling;

import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.selling.SellingDelivery;
import com.dragonappear.inha.service.selling.SellingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SellingDeliveryWebController {
    private final SellingService sellingService;

    @GetMapping("/web/sellings/deliveries/{sellingId}")
    public String findSellingDeliveryTest(@PathVariable("sellingId") Long sellingId, Model model) throws IOException {
        Selling selling = sellingService.findBySellingId(sellingId);
        SellingDelivery delivery = selling.getSellingDelivery();

        String t_key = "k5kVWOLhTjQ9TNSDpBM8iw";
        String t_code = delivery.getDelivery().getCourierName().getCode();
        String t_invoice = delivery.getDelivery().getInvoiceNumber();
        model.addAttribute("t_key",t_key);
        model.addAttribute("t_code",t_code);
        model.addAttribute("t_invoice",t_invoice);
        return "delivery/deliveryResult";
    }
}
