package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesById(Integer[] rolesId) {
        List<Role> roleResult = new ArrayList<>();
        if (rolesId == null) {
            roleResult.add(roleRepository.getById(1));
        } else {
            for (int id : rolesId) {
                Role result = roleRepository.getById(id);
                roleResult.add(result);
            }
        }
        return roleResult;
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
