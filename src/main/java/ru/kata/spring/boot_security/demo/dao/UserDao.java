package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserDao {
    void add(User user);

    void delete(int id);

    User getUser(int id);

    List<User> getAllUsers();

    void updateUser(int id, User newUser);

    User findByUsername(String username);
}
