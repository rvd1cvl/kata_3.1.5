package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao adminDao;


    public UserServiceImpl(UserDao adminDao) {
        this.adminDao = adminDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        adminDao.add(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        adminDao.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id) {
        return adminDao.getUser(id);
    }

    @Transactional
    @Override
    public void updateUser(int id, User newUser) {
        adminDao.updateUser(id, newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return adminDao.getAllUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        return adminDao.findByUsername(username);
    }
}
