package com.example.crudlibrary.model.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "Ad və soyad boş ola bilməz.")
    @Size(max = 50, message = "Ad və soyad maksimum 50 simvol ola bilər.")
    @Pattern(
            regexp = "^[^<>%]{1,50}$",
            message = "Ad və soyadda '<', '>' və '%' simvolları olmamalıdır."
    )
    private String fullName;

    @NotBlank(message = "Email boş ola bilməz.")
    @Email(message = "Doğru email formatı tələb olunur.")
    private String email;

    @NotBlank(message = "Parol boş ola bilməz.")
    @Pattern(
            regexp = "^(?=.*[a-zA-ZəöüıçşğƏÖÜIÇŞĞ])(?=.*[A-ZƏÖÜIÇŞĞ])(?=.*[a-zəöüıçşğ])(?=.*\\d)(?=.*[^a-zA-ZəöüıçşğƏÖÜIÇŞĞ\\d<>%\\s])[^<>%\\s]{8,50}$",
            message = "Parol 8-50 simvol arasında olmalı, ən azı bir böyük hərf, bir kiçik hərf, bir rəqəm və bir xüsusi simvol daxil etməlidir. '<', '>', '%', və boşluq simvolları qadağandır."
    )
    private String password;
}
