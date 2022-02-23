package com.boun.todo.service.userDetails;


import com.boun.todo.dto.AuthRequest;
import com.boun.todo.dto.LoginResponseDto;
import com.boun.todo.dto.RegisterRequestDto;
import com.boun.todo.dto.ResponseDto;
import com.boun.todo.entity.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    ResponseDto registerUser(RegisterRequestDto userDto);

    Users getUserInfo();

    LoginResponseDto loginUser(AuthRequest auth);

}
