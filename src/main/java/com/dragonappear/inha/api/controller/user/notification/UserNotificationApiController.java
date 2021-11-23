package com.dragonappear.inha.api.controller.user.notification;

import com.dragonappear.inha.api.controller.user.notification.dto.NotificationApiDto;
import com.dragonappear.inha.api.returndto.ResultDto;
import com.dragonappear.inha.service.user.UserNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.dragonappear.inha.api.returndto.ResultDto.returnResults;

@Api(tags = {"유저 알림내역 API"})
@RequiredArgsConstructor
@RestController
public class UserNotificationApiController {
    private final UserNotificationService userNotificationService;

    @ApiOperation(value = "유저 알림내역 조회 API", notes = "유저 알림내역 조회")
    @GetMapping("/api/v1/users/notifications/{userId}")
    public ResultDto getUserAllNotifications(@PathVariable("userId") Long userId) {
        List<NotificationApiDto> dtos = userNotificationService.findAllByUserId(userId).stream()
                .map(userNotification -> {
                    return NotificationApiDto.builder()
                            .whenCreated(userNotification.getCreatedDate())
                            .title(userNotification.getTitle())
                            .body(userNotification.getBody())
                            .build();
                }).collect(Collectors.toList());
        return returnResults(dtos);
    }
}
