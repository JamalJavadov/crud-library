package com.example.crudlibrary.model.dto.userDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    private String fullName;

    private String password;

}
