package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User getById(int id);
    User getByUsername(String username);
    List<User> findAll();
    List<Role> getAllRoles();
    List<Role> getRolesById(Integer[] rolesId);
    void saveUser(User user);
    void updateUser(User updatedUser, Integer oldUserId);
    void saveRole(Role role);
    void addUser(User user);
    void deleteById(Integer id);
}