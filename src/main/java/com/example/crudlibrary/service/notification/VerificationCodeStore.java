package com.example.crudlibrary.service.notification;

import com.example.crudlibrary.model.dto.code.CodeEntryDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VerificationCodeStore {
    private static final long EXPIRATION_TIME_MS = 3 * 60 * 1000;
    private final Map<String, CodeEntryDto> codeMap = new ConcurrentHashMap<>();

    public void saveCode(String email, String code) {
        codeMap.put(email, new CodeEntryDto(code,System.currentTimeMillis()));
    }

    public boolean isCodeValid(String email, String code) {
        CodeEntryDto entry = codeMap.get(email);
        if (entry==null){
            return false;
        }
        long now = System.currentTimeMillis();
        boolean notExpired = (now-entry.getTimestamp() <=EXPIRATION_TIME_MS);
        return notExpired && entry.getCode().equals(code);
    }

    public void removeCode(String email) {
        codeMap.remove(email);
    }
}
