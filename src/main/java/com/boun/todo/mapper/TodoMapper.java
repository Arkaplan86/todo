package com.boun.todo.mapper;


import com.boun.todo.dto.TodoDto;
import com.boun.todo.entity.Todo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(injectionStrategy = InjectionStrategy.FIELD, componentModel = "spring")
public interface TodoMapper {

    Todo toTodo(TodoDto todoDto);

    TodoDto toTodoDto(Todo todoModel);

    List<Todo> toTodoList(List<TodoDto> todoDtos);

    List<TodoDto> toTodoDtoList(List<Todo> todoModels);

}
