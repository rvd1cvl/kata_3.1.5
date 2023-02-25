package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
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

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserDto userDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.addUser(convertToUser(userDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println("User with this data already exists");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Integer oldUserId,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateUser(convertToUser(userDto), oldUserId);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User convertToUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }
}
