package com.dragonappear.inha.domain.user.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER("ROLE_USER","일반"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
