package com.dragonappear.inha.web.rtc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RtcController {

    @GetMapping("/web/rtc")
    private String rtcTest() {
        return "rtc/rtc";
    }
}
