package com.boun.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {
    private Long parentTodoId;
    @NotNull(message = "name can not be null!")
    private String name;
    @NotNull(message = "description can not be null!")
    private String description;
    private String deadLine;
}
