package com.boun.todo.controller;


import com.boun.todo.dto.ResponseDto;
import com.boun.todo.dto.TodoDto;
import com.boun.todo.dto.TodoRequestDto;
import com.boun.todo.service.todoService.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @RequestMapping(value = "/box", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> addTodo(@Valid @RequestBody TodoRequestDto requestDto) {
        ResponseDto response = todoService.saveTodo(requestDto);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/box/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseDto> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto requestDto) {
        ResponseDto response = todoService.updateTodo(id, requestDto);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/box", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseDto> deleteTodo(@Valid @RequestBody List<Long> ids) {
        ResponseDto response = todoService.removeTodo(ids);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/box/{id}", method = RequestMethod.GET)
    public ResponseEntity<TodoDto> getTodo(@PathVariable Long id) {
        TodoDto todo = todoService.getTodo(id);
        return new ResponseEntity<>(todo, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/box", method = RequestMethod.GET)
    public ResponseEntity<Page<TodoDto>> getTodos(@RequestParam(defaultValue = "0") int pageNo,
                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "id") String sortBy) {
        Page<TodoDto> todos = todoService.getTodos(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(todos, new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/box/day", method = RequestMethod.GET)
    public ResponseEntity<Page<TodoDto>> getDayOfAllTodo(@RequestParam(defaultValue = "0") int pageNo,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "id") String sortBy) {
        Page<TodoDto> todos = todoService.getAllTodoByDay(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(todos, new HttpHeaders(), HttpStatus.OK);
    }


    @RequestMapping(value = "/box/week", method = RequestMethod.GET)
    public ResponseEntity<Page<TodoDto>> getWeekOfAllTodo(@RequestParam(defaultValue = "0") int pageNo,
                                                          @RequestParam(defaultValue = "10") int pageSize,
                                                          @RequestParam(defaultValue = "id") String sortBy) {
        Page<TodoDto> todos = todoService.getAllTodoByWeek(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(todos, new HttpHeaders(), HttpStatus.OK);
    }

}
