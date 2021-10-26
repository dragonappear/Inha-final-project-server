package com.dragonappear.inha.api.returndto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class MessageDto {
    private Map<String, Object> message;

    @Builder
    public MessageDto(Map<String, Object> message) {
        this.message = message;
    }

    public static Map<String, Object> getMessage(String insert, Boolean bool, String status, String content) {
        Map<String, Object> result = new HashMap<>();
        result.put(insert, bool);
        result.put(status, content);
        return result;
    }


    public static Map<String, Object> getMessage(String insert, Boolean bool, String status, Object content,String role, String grade) {
        Map<String, Object> result = new HashMap<>();
        result.put(insert, bool);
        result.put(status, content);
        result.put(role, grade);
        return result;
    }
}