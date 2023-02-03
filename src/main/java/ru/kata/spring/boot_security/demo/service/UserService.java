package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);
    void delete(int id);
    User getUser(int id);
    void updateUser(int id, User newUser);
    List<User> getAllUsers();
    UserDetails loadUserByUsername(String username);
}
