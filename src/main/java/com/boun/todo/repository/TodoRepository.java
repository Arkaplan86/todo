package com.boun.todo.repository;

import com.boun.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findByIdAndUserId(Long id, Long userId);

    Optional<Todo> findTodoByIdAndUserId(Long id, Long userId);

    Optional<List<Todo>> findTodosByUserIdAndIdIn(Long userId, List<Long> ids);

    Page<Todo> findTodosByUserIdAndNameIsNotIn(Long userId, List<String> names, Pageable pageable);

    Page<Todo> findTodosByUserIdAndDeadLineEqualsAndNameIsNotIn(Long userId, LocalDate date, List<String> names, Pageable pageable);

    Page<Todo> findTodosByUserIdAndDeadLineBetweenAndNameIsNotIn(Long userId, LocalDate startDate, LocalDate endDate, List<String> names, Pageable pageable);

}
