package com.example.crudlibrary.model.dto.code;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCodeVerficationDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String code;
}
