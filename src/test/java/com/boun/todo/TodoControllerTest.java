package com.boun.todo;


import com.boun.todo.controller.TodoController;
import com.boun.todo.dto.ResponseDto;
import com.boun.todo.dto.TodoDto;
import com.boun.todo.dto.TodoRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TodoControllerTest {

    @Mock
    TodoController todoController;

    List<TodoDto> todoDtoList;

    TodoRequestDto todoRequestDto;

    TodoDto todoDto;

    ResponseDto responseDto;

    List<Long> ids;

    List<TodoDto> expTodoDtoList;

    TodoDto expTodoDto;

    ResponseDto expResponseDto;

    List<Long> expIds;

    @BeforeEach
    public void setUpData() {
        todoDto = new TodoDto();
        todoDto.setId(1L);
        todoDto.setName("test name");
        todoDto.setDescription("test description");

        todoDto.setDeadLine("2022-04-08");

        responseDto = new ResponseDto();
        responseDto.setMessage("test message");
        responseDto.setStatus(Boolean.TRUE);

        ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        todoRequestDto = new TodoRequestDto();
        todoRequestDto.setName("test");
        todoRequestDto.setDescription("test");
        todoRequestDto.setParentTodoId(1L);
        todoRequestDto.setDeadLine("2022-04-08");

        todoDtoList = new ArrayList<>();
        todoDtoList.add(todoDto);

    }

    @BeforeEach
    public void setUpExpected() {
        expTodoDto = new TodoDto();
        expTodoDto.setId(1L);
        expTodoDto.setName("test name");
        expTodoDto.setDescription("test description");
        expTodoDto.setDeadLine("2022-04-08");

        expResponseDto = new ResponseDto();
        expResponseDto.setMessage("test message");
        expResponseDto.setStatus(Boolean.TRUE);

        expIds = new ArrayList<>();
        expIds.add(1L);
        expIds.add(2L);

        expTodoDtoList = new ArrayList<>();
        expTodoDtoList.add(expTodoDto);
    }

    @Test
    public void addTodoThenReturnJson() {
        ResponseEntity<ResponseDto> result = ResponseEntity.ok(responseDto);
        when(todoController.addTodo(todoRequestDto)).thenReturn(new ResponseEntity<>(expResponseDto, new HttpHeaders(), HttpStatus.OK));
        ResponseEntity<ResponseDto> response = todoController.addTodo(todoRequestDto);
        assertThat(response).isEqualTo(result);
    }

    @Test
    public void updateTodoThenReturnResponse() {
        ResponseEntity<ResponseDto> result = ResponseEntity.ok(responseDto);
        when(todoController.updateTodo(1L, todoRequestDto)).thenReturn(new ResponseEntity<>(expResponseDto, new HttpHeaders(), HttpStatus.OK));
        ResponseEntity<ResponseDto> response = todoController.updateTodo(1L, todoRequestDto);
        assertThat(response).isEqualTo(result);
    }

    @Test
    public void deleteTodoThenReturnResponse() {
        ResponseEntity<ResponseDto> result = ResponseEntity.ok(responseDto);
        when(todoController.deleteTodo(ids)).thenReturn(new ResponseEntity<>(expResponseDto, new HttpHeaders(), HttpStatus.OK));
        ResponseEntity<ResponseDto> response = todoController.deleteTodo(ids);
        assertThat(response).isEqualTo(result);
    }

    @Test
    public void getTodosThenReturnResponse() {
        Pageable page = PageRequest.of(0, 10, Sort.by("id"));
        ResponseEntity<Page<TodoDto>> expected = new ResponseEntity<>(new PageImpl<>(expTodoDtoList, page, 0), new HttpHeaders(), HttpStatus.OK);
        when(todoController.getTodos(0, 10, "id")).thenReturn(new ResponseEntity<>(new PageImpl<>(todoDtoList, page, 0), new HttpHeaders(), HttpStatus.OK));
        ResponseEntity<Page<TodoDto>> response = todoController.getTodos(0, 10, "id");
        assertThat(response).isEqualTo(expected);
    }

}
