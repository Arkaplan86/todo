package com.boun.todo.mapper;


import com.boun.todo.dto.TodoRequestDto;
import com.boun.todo.entity.Todo;
import com.boun.todo.entity.Users;
import com.boun.todo.exception.TodoException;
import com.boun.todo.repository.TodoRepository;
import com.boun.todo.service.userDetails.CustomUserDetailService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.boun.todo.constant.Constant.*;


@NoArgsConstructor
@AllArgsConstructor
@Mapper(injectionStrategy = InjectionStrategy.FIELD, componentModel = "spring")
public abstract class TodoCustomMapper {

    @Autowired
    private TodoRepository todoRepository;

    @Resource(name = "userDetailService")
    private CustomUserDetailService userDetailsService;

    public Todo toTodoRequest(TodoRequestDto todoRequestDto) throws TodoException {
        Users user = userDetailsService.getUserInfo();
        Todo todo = new Todo();
        setTodo(todo, todoRequestDto);
        todo.setUser(user);
        Todo parentTodo = todoRepository.findTodoByIdAndUserId(todoRequestDto.getParentTodoId(), user.getId())
                .orElseThrow(() -> new TodoException("TodoRequestMapper::toTodoRequest error : todo not found!"));
        todo.setTodo(parentTodo);
        return todo;
    }

    public Todo toTodoUpdateRequest(TodoRequestDto todoRequestDto, Todo todo) throws TodoException {
        Users user = userDetailsService.getUserInfo();
        setTodo(todo, todoRequestDto);
        Todo parentTodo = todoRepository.findTodoByIdAndUserId(todoRequestDto.getParentTodoId(), user.getId())
                .orElseThrow(() -> new TodoException("TodoRequestMapper::toTodoUpdateRequest error : todo not found!"));
        todo.setTodo(parentTodo);
        return todo;
    }

    public List<Todo> toTodoInitialList(Users user) {
        List<Todo> todoList = new ArrayList<>();
        List<String> descriptions = new ArrayList<>(Arrays.asList(INBOX, PROJECT));
        for (String description : descriptions) {
            Todo todo = new Todo();
            todo.setUser(user);
            todo.setName(description);
            todo.setDescription(API_DEFAULT + description);
            todo.setDeadLine(null);
            todo.setCreateDate(LocalDate.now());
            todoList.add(todo);
        }
        return todoList;
    }

    private void setTodo(Todo todo, TodoRequestDto todoRequestDto) {
        todo.setName(todoRequestDto.getName());
        todo.setDescription(todoRequestDto.getDescription());
        todo.setCreateDate(LocalDate.now());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(todoRequestDto.getDeadLine(), dateTimeFormatter);
        todo.setDeadLine(localDate);
    }
}
