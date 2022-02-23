package com.boun.todo.mapper;


import com.boun.todo.dto.RegisterRequestDto;
import com.boun.todo.entity.Users;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.FIELD, componentModel = "spring")
public interface UserMapper {

    Users toUser(RegisterRequestDto registerRequestDto);

}
