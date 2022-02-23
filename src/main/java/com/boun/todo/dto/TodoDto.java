package com.boun.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto extends BaseDto {
    private String deadLine;
    private List<TodoDto> subTodos;
}
