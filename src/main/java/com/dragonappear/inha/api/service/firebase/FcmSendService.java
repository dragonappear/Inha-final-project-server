package com.dragonappear.inha.api.service.firebase;

import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.user.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmSendService {
    private final FirebaseCloudMessageService fcmService;
    private final UserTokenService userTokenService;

    public void sendFCM(User user,String title,String body) throws Exception {
        byte[] decode = Base64.getDecoder().decode(userTokenService.findTokenByUserIdAndType(user.getId(), "fcm"));
        String token = new String(decode, StandardCharsets.UTF_8);
        fcmService.sendMessageTo(token,title,body);
    }
}
