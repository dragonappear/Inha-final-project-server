package com.dragonappear.inha.web.admin.delivery;

import com.dragonappear.inha.api.service.firebase.FcmSendService;
import com.dragonappear.inha.domain.inspection.Inspection;
import com.dragonappear.inha.domain.inspection.fail.FailDelivery;
import com.dragonappear.inha.domain.inspection.fail.FailInspection;
import com.dragonappear.inha.domain.inspection.pass.PassDelivery;
import com.dragonappear.inha.domain.inspection.pass.PassInspection;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.selling.Selling;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.user.UserAddress;
import com.dragonappear.inha.domain.value.Delivery;
import com.dragonappear.inha.service.inspection.InspectionService;
import com.dragonappear.inha.service.inspection.fail.FailDeliveryService;
import com.dragonappear.inha.service.inspection.pass.PassDeliveryService;
import com.dragonappear.inha.service.user.UserAddressService;
import com.dragonappear.inha.web.repository.DealWebRepository;
import com.dragonappear.inha.web.repository.dto.ReturnDealWebDto;
import com.dragonappear.inha.web.repository.dto.SendDealWebDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/web/admin/deliveries")
public class DeliveryWebController {
    private final FcmSendService fcmSendService;
    private final DealWebRepository dealWebRepository;
    private final InspectionService inspectionService;
    private final UserAddressService userAddressService;
    private final PassDeliveryService passDeliveryService;
    private final FailDeliveryService failDeliveryService;

    @GetMapping("/sends")
    public String sendList(Model model) {
        List<SendDealWebDto> sendList = dealWebRepository.getSendList();
        model.addAttribute("list", sendList);
        return "delivery/sendList";
    }

    @PostMapping("/sends/{inspectionId}")
    public String sendRegister(@PathVariable("inspectionId") Long inspectionId, Model model) {
        model.addAttribute("inspectionId", inspectionId);
        return "delivery/sendRegister";
    }

    @GetMapping("/returns")
    public String returnList(Model model) {
        List<ReturnDealWebDto> returnList = dealWebRepository.getReturnList();
        model.addAttribute("list", returnList);
        return "delivery/returnList";
    }

    @PostMapping("/returns/{inspectionId}")
    public String returnRegister(@PathVariable("inspectionId") Long inspectionId, Model model) {
        model.addAttribute("inspectionId", inspectionId);
        return "delivery/returnRegister";
    }

    @PostMapping("/register/{inspectionId}")
    public String deliveryRegister(@PathVariable("inspectionId") Long inspectionId, @RequestParam DeliveryWebDto dto) {
        Inspection inspection = inspectionService.findById(inspectionId);
        if (inspection instanceof PassInspection) {
            Payment payment = inspection.getDeal().getBuying().getPayment();
            Long addressId = payment.getAddressId();
            User user = payment.getUser();
            UserAddress userAddress = userAddressService.findByUserAddressId(addressId);
            Delivery delivery = new Delivery(dto.getCourierName(), dto.getDeliveryNumber());
            passDeliveryService.save(new PassDelivery(delivery, userAddress.getUserAddress(), (PassInspection)inspection));
            String title = "아이템 배송알림";
            String body = "주문하신 " + payment.getAuctionitem().getItem().getItemName() + "의 배송이 시작되었습니다";
            sendMessage(user,title,body,inspectionId);
            return "redirect:/web/admin/sends";
        } else {
            Selling selling = inspection.getDeal().getSelling();
            Long addressId = selling.getSeller().getUserAddresses().get(0).getId();
            UserAddress userAddress = userAddressService.findByUserAddressId(addressId);
            Delivery delivery = new Delivery(dto.getCourierName(), dto.getDeliveryNumber());
            failDeliveryService.save(new FailDelivery(delivery, userAddress.getUserAddress(), (FailInspection)inspection));
            String title = "아이템 반송알림";
            String body = "주문하신 " + selling.getAuctionitem().getItem().getItemName() + "의 반송이 시작되었습니다";
            sendMessage(selling.getSeller(),title,body,inspectionId);
            return "redirect:/web/admin/returns";
        }
    }

    public void sendMessage(User user, String title, String body, Long inspectionId) {
        try {
            fcmSendService.sendFCM(user, title, body);
        } catch (Exception e) {
            log.error("inspectionId:{} 배송 FCM 메시지가 전송되지 않았습니다.",inspectionId);
        }
    }
}
