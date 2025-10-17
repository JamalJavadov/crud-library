package com.example.crudlibrary.controller;


import com.example.crudlibrary.model.dto.auth.AuthenticationRequestDto;
import com.example.crudlibrary.model.dto.auth.AuthenticationResponseDto;
import com.example.crudlibrary.model.dto.code.AuthCodeVerficationDto;
import com.example.crudlibrary.model.dto.userDto.UserRequestDto;
import com.example.crudlibrary.service.securityservice.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthenticationController {
    private final AuthenticationService authenticationService;

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

}
