package com.boun.todo.service.userDetails;


import com.boun.todo.dto.AuthRequest;
import com.boun.todo.dto.LoginResponseDto;
import com.boun.todo.dto.RegisterRequestDto;
import com.boun.todo.dto.ResponseDto;
import com.boun.todo.entity.Users;
import com.boun.todo.mapper.UserCustomMapper;
import com.boun.todo.repository.UserRepository;
import com.boun.todo.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.boun.todo.constant.Constant.*;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service(value = "userDetailService")
public class CustomUserDetailServiceImpl implements UserDetailsService, CustomUserDetailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCustomMapper userCustomMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username);
        if (Objects.isNull(users)) {
            log.error("CustomUserDetailServiceImpl::loadUserByUsername::error : username or password not valid!");
            throw new UsernameNotFoundException(USERNAME_OR_PASSWORD_NOT_VALID);
        }
        return new User(users.getUsername(), users.getPassword(), getAuthority(users));
    }

    @Override
    @Transactional
    public ResponseDto registerUser(RegisterRequestDto registerRequest) {
        try {
            Users existedUser = userRepository.findByUsername(registerRequest.getUsername());
            if (!Objects.isNull(existedUser)) {
                log.error("CustomUserDetailServiceImpl::registerUser::error : User already exist!");
                return new ResponseDto(USER_ALREADY_EXIST, Boolean.FALSE);
            }
            Users user = userCustomMapper.toUser(registerRequest);
            if (Objects.isNull(user)) {
                return new ResponseDto(ROLE_NOT_FOUND, Boolean.FALSE);
            }
            userRepository.save(user);
            return new ResponseDto(USER_CREATED, Boolean.TRUE);
        } catch (Exception e) {
            log.error("CustomUserDetailServiceImpl::registerUser::error : {}", registerRequest, e);
            return new ResponseDto(REGISTER_FAILURE, Boolean.FALSE);
        }
    }

    @Override
    public LoginResponseDto loginUser(AuthRequest auth) {
        try {
            if (Objects.isNull(auth.getUsername()) || Objects.isNull(auth.getPassword())) {
                log.error("CustomUserDetailServiceImpl::loginUser::error : uername or password can not be null!");
                return new LoginResponseDto(auth.getUsername(), null, USERNAME_OR_PASSWORD_NOT_EXIST, Boolean.FALSE);
            }
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getUsername(),
                            auth.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenUtil.generateToken(authentication);
            return new LoginResponseDto(auth.getUsername(), token, LOGIN_SUCCESSFUL, Boolean.TRUE);
        } catch (Exception e) {
            log.error("CustomUserDetailServiceImpl::loginUser::error for username : {}", auth.getUsername(), e);
            return new LoginResponseDto(auth.getUsername(), null, LOGIN_FAILURE, Boolean.FALSE);
        }
    }

    @Override
    public Users getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserDetails userDetails = loadUserByUsername(currentPrincipalName);
        return userRepository.findByUsername(userDetails.getUsername());
    }

    private Set<SimpleGrantedAuthority> getAuthority(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        });
        return authorities;
    }
}

