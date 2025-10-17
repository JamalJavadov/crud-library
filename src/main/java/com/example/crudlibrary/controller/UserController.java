package com.example.crudlibrary.controller;

import com.example.crudlibrary.model.dto.userDto.UserResponseDto;
import com.example.crudlibrary.model.dto.userDto.UserUpdateDto;
import com.example.crudlibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateDto userUpdateDto, Authentication authentication){
        return ResponseEntity.ok(userService.update(userUpdateDto,authentication.getName()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(Authentication authentication){
        return ResponseEntity.ok(userService.profile(authentication.getName()));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam String email){
        userService.delete(email);
    }

    @DeleteMapping("/safe-delete")
    public void safeDelete(@RequestParam String email){
        userService.safeDelete(email);
    }

}
