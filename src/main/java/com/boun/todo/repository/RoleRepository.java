package com.boun.todo.repository;


import com.boun.todo.entity.Role;
import com.boun.todo.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleModelByName(RoleEnum name);
}
