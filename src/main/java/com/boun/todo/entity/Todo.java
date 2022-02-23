package com.boun.todo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TODO")
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DEADLINE")
    private LocalDate deadLine;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<Todo> subTodos = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private Todo todo;

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", deadLine=" + deadLine +
                ", subTodos=" + subTodos +
                '}';
    }

}
