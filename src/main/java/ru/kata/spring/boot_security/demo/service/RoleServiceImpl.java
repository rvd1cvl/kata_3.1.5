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
    private final EntityManager entityManager;

    public RoleServiceImpl(RoleRepository roleRepository, EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesById(Integer[] rolesId) {
        List<Role> roleResult = new ArrayList<>();
        if (rolesId == null) {
            roleResult.add(entityManager.find(Role.class, 1));
        } else {
            for (int id : rolesId) {
                TypedQuery<Role> query = entityManager.createQuery("select role from Role role where role.id= :id", Role.class)
                        .setParameter("id", id);
                Role result = query.getResultList().stream().filter(role -> role.getId() == id).findAny().orElse(null);
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
