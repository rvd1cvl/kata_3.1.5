package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }


    public User getById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public List<Role> getAllRoles() {
        return new ArrayList<>(roleRepository.findAll());
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

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User newUser, Integer oldUserId) {
        User oldUser = entityManager.createQuery("select user from User user where user.id = :id", User.class)
                .setParameter("id", oldUserId).getSingleResult();
        oldUser.setEmail(newUser.getEmail());
        oldUser.setId(oldUserId);
        oldUser.setName(newUser.getName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setRoles(newUser.getRoles());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setAge(newUser.getAge());
        userRepository.save(oldUser);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

}
