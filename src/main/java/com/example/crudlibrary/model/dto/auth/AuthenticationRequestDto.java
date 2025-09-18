package com.example.crudlibrary.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-zA-ZəöüıçşğƏÖÜIÇŞĞ])(?=.*[A-ZƏÖÜIÇŞĞ])(?=.*[a-zəöüıçşğ])(?=.*\\d)(?=.*[^a-zA-ZəöüıçşğƏÖÜIÇŞĞ\\d<>%\\s])[^<>%\\s]{8,50}$",
            message = "Parol 8-50 simvol arasında olmalı, ən azı bir böyük hərf, bir kiçik hərf, bir rəqəm və bir xüsusi simvol daxil etməlidir. '<', '>', '%', və boşluq simvolları qadağandır."
    )    String password;

    @Email
    @NotBlank
    private String email;
}
