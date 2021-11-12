package com.dragonappear.inha.api.service.firebase;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.service.user.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmSendService {
    private final FirebaseCloudMessageService fcmService;
    private final UserTokenService userTokenService;

    public void sendFCM(User user,String title,String body) throws IOException {
        String token = userTokenService.findTokenByUserIdAndType(user.getId(), "fcm");
        fcmService.sendMessageTo(token,title,body);
    }
}
