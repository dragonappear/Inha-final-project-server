package com.dragonappear.inha.api.controller.user.mypage.update.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class ImageDto<T> {
    private Map<String, Object> map;
    private T content;

    @Builder
    public ImageDto(Map<String, Object> map, T content) {
        this.map = map;
        this.content = content;
    }
    public static Map<String, Object> getDto(String insert, Boolean bool, String status, String content) {
        Map<String, Object> result = new HashMap<>();
        result.put(insert, bool);
        result.put(status, content);
        return result;
    }
}

