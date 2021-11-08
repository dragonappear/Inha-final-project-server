package com.dragonappear.inha.api.service.firebase;

import com.dragonappear.inha.api.service.firebase.dto.FcmMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FirebaseCloudMessageService {
    private final ObjectMapper objectMapper;

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/m1no-test/messages:send";

    public String getAccessToken() throws IOException {
        //FileInputStream inputStream = new FileInputStream("src/main/resources/firebase/firebaseAccountKey.json");
        FileInputStream inputStream = new FileInputStream("/home/ec2-user/app/step1/Inha-final-project-server/src/main/resources/firebase/firebaseAccountKey.json");
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(inputStream)
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void sendMessageTo(String token, String title, String body) throws IOException {
        String message = makeMessage(token, title, body);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), message);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();

        log.info(response.body().toString());
    }

    private String makeMessage(String token, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(token)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build())
                        .build())
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
}
