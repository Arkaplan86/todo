package com.boun.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequestDto {
    private Long id;
    @NotNull(message = "username can not be null!")
    private String username;
    @NotNull(message = "password can not be null!")
    private String password;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;

}
