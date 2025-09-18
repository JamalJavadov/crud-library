package com.example.crudlibrary.service.securityservice;
import com.example.crudlibrary.exception.RefreshTokenExpired;
import com.example.crudlibrary.exception.RefreshTokenNotFound;
import com.example.crudlibrary.model.dto.refreshtoken.TokenRefreshRequestDto;
import com.example.crudlibrary.model.dto.refreshtoken.TokenRefreshResponseDto;
import com.example.crudlibrary.model.entity.User;
import com.example.crudlibrary.model.entity.UserRefreshToken;
import com.example.crudlibrary.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
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

    public TokenRefreshResponseDto refreshToken(TokenRefreshRequestDto trq){
        return refreshTokenRepository.findRefreshTokenByToken(trq.getRefreshToken())
                .map(token->{
            if (isExpired(token)){
                refreshTokenRepository.delete(token);
                throw new RefreshTokenExpired("Refresh Token Expired");
            }
            User user = token.getUser();
            String newAccessToken = jwtService.generatedToken(user);
            return ResponseEntity.ok(new TokenRefreshResponseDto(newAccessToken,token.getToken()));
        }).orElseThrow(()-> new RefreshTokenNotFound("Refresh Token Not Found")).getBody();
    }
    @Transactional
    public String logout(TokenRefreshRequestDto trq){
        return refreshTokenRepository.findRefreshTokenByToken(trq.getRefreshToken())
                .map(refreshToken -> {
                    deleteByUser(refreshToken.getUser());
                    return ResponseEntity.ok("User logged out successfully");
                }).orElseThrow(()-> new RefreshTokenNotFound("Refresh Token Not Found")).getBody();
    }
}
