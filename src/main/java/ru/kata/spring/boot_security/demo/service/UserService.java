package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User getById(int id);
    User getByUsername(String username);
    List<User> findAll();
    void saveUser(User user);
    void updateUser(User updatedUser, Integer oldUserId);
    void addUser(User user);
    void deleteById(Integer id);
    void remove(User uSer);
}