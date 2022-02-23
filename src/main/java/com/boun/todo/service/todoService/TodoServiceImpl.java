package com.boun.todo.service.todoService;


import com.boun.todo.dto.ResponseDto;
import com.boun.todo.dto.TodoDto;
import com.boun.todo.dto.TodoRequestDto;
import com.boun.todo.entity.Todo;
import com.boun.todo.entity.Users;
import com.boun.todo.exception.TodoException;
import com.boun.todo.mapper.TodoCustomMapper;
import com.boun.todo.mapper.TodoMapper;
import com.boun.todo.repository.TodoRepository;
import com.boun.todo.service.userDetails.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.boun.todo.constant.Constant.*;


@Slf4j
@Service(value = "todoService")
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Resource(name = "userDetailService")
    private final CustomUserDetailService userDetailsService;

    private final TodoMapper todoMapper;

    private final TodoCustomMapper todoCustomMapper;


    @Transactional
    @Override
    public ResponseDto saveTodo(TodoRequestDto requestDto) {
        try {
            todoRepository.save(todoCustomMapper.toTodoRequest(requestDto));
            return new ResponseDto(TODO_CREATED, Boolean.TRUE);
        } catch (Exception e) {
            log.info("TodoServiceImpl::saveTodo::error : {}", requestDto, e);
            return new ResponseDto(CREATE_TODO_FAILURE, Boolean.FALSE);
        }
    }

    @Transactional
    @Override
    public ResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
        try {
            Users user = userDetailsService.getUserInfo();
            Todo todo = todoRepository.findByIdAndUserId(id, user.getId());
            if (Objects.isNull(todo)) {
                log.error("TodoServiceImpl::updateTodo::error : todo not found!");
                return new ResponseDto(TODO_NOT_FOUND, Boolean.FALSE);
            }
            todoRepository.save(todoCustomMapper.toTodoUpdateRequest(requestDto, todo));
            return new ResponseDto(TODO_UPDATED, Boolean.TRUE);
        } catch (Exception e) {
            log.error("TodoServiceImpl::updateTodo::error : {}", requestDto, e);
            return new ResponseDto(UPDATE_TODO_FAILURE, Boolean.FALSE);
        }
    }

    @Transactional
    @Override
    public ResponseDto removeTodo(List<Long> ids) {
        try {
            Users user = userDetailsService.getUserInfo();
            List<Todo> todos = todoRepository.findTodosByUserIdAndIdIn(user.getId(), ids)
                    .orElseThrow(() -> new TodoException("Todo not found!"));

            List<Todo> todoFilterList = todos.stream()
                    .filter(todo -> todo.getName().equals(INBOX) || todo.getName().equals(PROJECT))
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(todoFilterList)) {
                log.error("TodoServiceImpl::removeTodo::error : {}", todoFilterList);
                return new ResponseDto(INBOX_OR_PROJECT_FAILURE, Boolean.FALSE);
            }

            todoRepository.deleteAll(todos);
            return new ResponseDto(TODO_DELETED, Boolean.TRUE);
        } catch (Exception e) {
            log.error("TodoServiceImpl::removeTodo::error : todo does not removed!", e);
            return new ResponseDto(DELETE_TODO_FAILURE, Boolean.FALSE);
        }
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        Users user = userDetailsService.getUserInfo();
        Todo todo = todoRepository.findByIdAndUserId(todoId, user.getId());
        if (Objects.nonNull(todo)) {
            todoMapper.toTodoDto(todo);
            return todoMapper.toTodoDto(todo);
        } else {
            return new TodoDto();
        }
    }

    @Override
    public Page<TodoDto> getTodos(int pageNo, int pageSize, String sortBy) {
        Users user = userDetailsService.getUserInfo();
        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Todo> todos = todoRepository.findTodosByUserIdAndNameIsNotIn(user.getId(), NAMES, page);
        if (todos.hasContent()) {
            return todos.map(todoMapper::toTodoDto);
        } else {
            return new PageImpl<>(new ArrayList<>(), page, 0);
        }
    }

    @Override
    public Page<TodoDto> getAllTodoByDay(int pageNo, int pageSize, String sortBy) {
        Users user = userDetailsService.getUserInfo();
        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Todo> todos = todoRepository.findTodosByUserIdAndDeadLineEqualsAndNameIsNotIn(user.getId(), LocalDate.now(), NAMES, page);
        if (todos.hasContent()) {
            return todos.map(todoMapper::toTodoDto);
        } else {
            return new PageImpl<>(new ArrayList<>(), page, 0);
        }
    }

    @Override
    public Page<TodoDto> getAllTodoByWeek(int pageNo, int pageSize, String sortBy) {
        Users user = userDetailsService.getUserInfo();
        LocalDate localDate = LocalDate.now();
        LocalDate startDate = localDate.minusDays(localDate.getDayOfWeek().getValue() - 1);
        LocalDate endDate = startDate.plusDays(6);

        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Todo> todos = todoRepository.findTodosByUserIdAndDeadLineBetweenAndNameIsNotIn(user.getId(), startDate, endDate, NAMES, page);
        if (todos.hasContent()) {
            return todos.map(todoMapper::toTodoDto);
        } else {
            return new PageImpl<>(new ArrayList<>(), page, 0);
        }
    }

}
