package com.dragonappear.inha.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value="/web/admin")
    public String home(){
        return "home";
    }
}

