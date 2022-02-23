package com.boun.todo.mapper;


import com.boun.todo.dto.LoginResponseDto;
import com.boun.todo.dto.RegisterRequestDto;
import com.boun.todo.entity.Role;
import com.boun.todo.entity.Users;
import com.boun.todo.enums.RoleEnum;
import com.boun.todo.service.roleService.RoleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Mapper(injectionStrategy = InjectionStrategy.FIELD, componentModel = "spring")
public abstract class UserCustomMapper {

    @Autowired
    private RoleService roleService;

    @Autowired
    private TodoCustomMapper todoCustomMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


    public Users toUser(RegisterRequestDto registerRequest) {
        Users user = userMapper.toUser(registerRequest);
        user.setPassword(bcryptEncoder.encode(registerRequest.getPassword()));
        Role role = roleService.findByName(RoleEnum.USER);
        if (Objects.isNull(role)) {
            log.error("UserCustomMapper::toUser::error : role can not be null!");
            return new Users();
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setTodoList(todoCustomMapper.toTodoInitialList(user));

        // TODO: 22.02.2022 change here to role based auth
        if (user.getEmail().split("@")[1].equals("admin.edu")) {
            role = roleService.findByName(RoleEnum.ADMIN);
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        return user;
    }

}
