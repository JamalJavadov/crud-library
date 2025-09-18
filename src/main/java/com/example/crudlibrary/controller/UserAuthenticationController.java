package com.example.crudlibrary.controller;


import com.example.crudlibrary.model.dto.auth.AuthenticationRequestDto;
import com.example.crudlibrary.model.dto.auth.AuthenticationResponseDto;
import com.example.crudlibrary.model.dto.code.AuthCodeVerficationDto;
import com.example.crudlibrary.model.dto.refreshtoken.TokenRefreshRequestDto;
import com.example.crudlibrary.model.dto.refreshtoken.TokenRefreshResponseDto;
import com.example.crudlibrary.model.dto.userDto.UserRequestDto;
import com.example.crudlibrary.model.entity.User;
import com.example.crudlibrary.repository.RefreshTokenRepository;
import com.example.crudlibrary.service.securityservice.AuthenticationService;
import com.example.crudlibrary.service.securityservice.JwtService;
import com.example.crudlibrary.service.securityservice.UserRefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(authenticationService.userRegister(request));
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticateSendCode(request));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<AuthenticationResponseDto> verifyCode(@Valid @RequestBody AuthCodeVerficationDto authCodeVerfication) {
        return ResponseEntity.ok(authenticationService.verifyCode(authCodeVerfication));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDto tokenRefresh){
        return refreshTokenRepository.findRefreshTokenByToken(tokenRefresh.getRefreshToken())
                .map(token->{
                    if (refreshTokenService.isExpired(token)){
                        refreshTokenRepository.delete(token);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired. Please log in again.");
                    }
                    User user = token.getUser();
                    String newAccessToken = jwtService.generatedToken(user);
                    return ResponseEntity.ok(new TokenRefreshResponseDto(newAccessToken,token.getToken()));

                })
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh Token Not Found"));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequestDto tokenRefreshRequestDto){
        return refreshTokenRepository.findRefreshTokenByToken(tokenRefreshRequestDto.getRefreshToken())
                .map(refreshToken -> {
                    refreshTokenService.deleteByUser(refreshToken.getUser());
                    return ResponseEntity.ok("User logged out successfully");
                }).orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh Token Not Found"));
    }
}
