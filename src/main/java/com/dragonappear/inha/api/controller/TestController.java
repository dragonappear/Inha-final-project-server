package com.dragonappear.inha.api.controller;

import com.dragonappear.inha.api.service.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final FirebaseCloudMessageService messageService;

    @GetMapping
    public String test() throws IOException {
        messageService.sendMessageTo("d3UC9HtvQD6FxwFJ8sF6GP:APA91bHpwwuDwZj8bWyCLHyvtyWgjZo71tO49zWDxPROtpcpv7fmDitk4WFJNIFmg8lzQCn9ygIzSgZ_VBerBvCRJBsraYwTeQ0Hz9nQJu0rCS6nGyV8mlEmykuJMy2WzZcKNa0F62sX",
                "title1","body1");

        return "1";
    }
}
