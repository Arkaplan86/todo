package com.boun.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "NAME", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 250, nullable = false)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;
}
