package com.example.crudlibrary.config;



import com.example.crudlibrary.model.entity.Role;
import com.example.crudlibrary.model.entity.User;
import com.example.crudlibrary.repository.UserRepository;
import com.example.crudlibrary.service.securityservice.JwtService;
import com.example.crudlibrary.service.securityservice.UserRefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFullName((String) oAuth2User.getAttributes().get("given_name"));
                    newUser.setPassword("oauth"); // OAuth ilə gələnlər üçün saxta şifrə
                    newUser.setRole(Role.USER);
                    return userRepository.save(newUser);
                });

        String token = jwtService.generatedToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();
        // ✅ Tokeni URL-ə əlavə edib frontend-ə yönləndiririk
//        String redirectUrl = "http://localhost:5500/google-success.html?token=" + token+"&refreshToken="+refreshToken;
        String redirectUrl = "http://127.0.0.1:5500/google-success.html?token=" + token+"&refreshToken="+refreshToken;
        response.sendRedirect(redirectUrl);
    }
}
