package com.boun.todo.service.todoService;


import com.boun.todo.dto.ResponseDto;
import com.boun.todo.dto.TodoDto;
import com.boun.todo.dto.TodoRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TodoService {

    ResponseDto saveTodo(TodoRequestDto todoRequestDto);

    ResponseDto updateTodo(Long id, TodoRequestDto requestDto);

    ResponseDto removeTodo(List<Long> Ids);

    TodoDto getTodo(Long todoId);

    Page<TodoDto> getTodos(int pageNo, int pageSize, String sortBy);

    Page<TodoDto> getAllTodoByDay(int pageNo, int pageSize, String sortBy);

    Page<TodoDto> getAllTodoByWeek(int pageNo, int pageSize, String sortBy);

}
