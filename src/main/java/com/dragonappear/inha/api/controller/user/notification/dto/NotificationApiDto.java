package com.dragonappear.inha.api.controller.user.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationApiDto {
    private LocalDateTime whenCreated;
    private String title;
    private String body;
}
