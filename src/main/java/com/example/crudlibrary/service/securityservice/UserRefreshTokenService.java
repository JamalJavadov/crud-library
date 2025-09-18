package com.example.crudlibrary.service.securityservice;
import com.example.crudlibrary.model.entity.User;
import com.example.crudlibrary.model.entity.UserRefreshToken;
import com.example.crudlibrary.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final long refreshTokenDurationMs = 7L * 24 * 60 * 60 * 1000;

    @Transactional
    public UserRefreshToken createRefreshToken(User user){
        deleteByUser(user);
        UserRefreshToken refreshToken = UserRefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public boolean isExpired(UserRefreshToken refreshToken){
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }

    @Transactional
    public void deleteByUser(User user){
        refreshTokenRepository.deleteByUser(user);
    }
}
