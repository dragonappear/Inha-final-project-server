package com.dragonappear.inha.api.service.firebase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class FcmMessage {
    private boolean validate_only;
    private Message message;

    @AllArgsConstructor
    @Builder
    @Data
    public static class Message {
        private Notification notification;
        private String token;
    }

    @AllArgsConstructor
    @Builder
    @Data
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
