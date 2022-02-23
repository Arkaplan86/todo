package com.boun.todo.service.roleService;


import com.boun.todo.entity.Role;
import com.boun.todo.enums.RoleEnum;

public interface RoleService {

     Role findByName(RoleEnum name);

}
