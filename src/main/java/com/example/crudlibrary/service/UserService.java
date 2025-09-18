package com.example.crudlibrary.service;

import com.example.crudlibrary.model.dto.userDto.UserResponseDto;
import com.example.crudlibrary.model.dto.userDto.UserUpdateDto;
import com.example.crudlibrary.model.entity.User;
import com.example.crudlibrary.model.mapper.UserMapper;
import com.example.crudlibrary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto profile(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        return userMapper.toDto(user);
    }

    public UserResponseDto update(UserUpdateDto userUpdateDto, String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        user = userMapper.toDto(userUpdateDto,user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

}
