package com.boun.todo.service.roleService;


import com.boun.todo.entity.Role;
import com.boun.todo.enums.RoleEnum;
import com.boun.todo.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleEnum name) {
        return roleRepository.findRoleModelByName(name);
    }

}
