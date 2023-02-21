package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @PersistenceContext
    private final EntityManager entityManager;

    public AdminController(UserService userService, UserRepository userRepository, RoleService roleService, EntityManager entityManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.entityManager = entityManager;
    }

    @GetMapping("/admin")
    public ResponseEntity<HttpStatus> showAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "users/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto) {
        userService.saveUser(convertToUser(userDto));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @DeleteMapping(value = "users/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody UserDto userDto) {
        userService.remove(convertToUser(userDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "users/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDto userDto, Integer oldUserId) {
        userService.updateUser(convertToUser(userDto), oldUserId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }
}
