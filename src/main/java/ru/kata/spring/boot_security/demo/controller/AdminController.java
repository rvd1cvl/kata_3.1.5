package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        List<User> users = userService.findAll();
        model.addAttribute("activeUser", userService.getByUsername(principal.getName()));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        List<Role> roles = userService.getAllRoles();
        model.addAttribute("roles", roles);
        return "adminPage";
    }


    @GetMapping(value = "users/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(name = "rolesSelected", defaultValue = "0") Integer[] rolesId) {
        Set<Role> roles = new HashSet<>(userService.getRolesById(rolesId));
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";

    }

    @GetMapping(value = "users/delete")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "users/update")
    public String updateUser(@ModelAttribute("user") User user, Integer[] rolesId) {
        Set<Role> roles = new HashSet<>(userService.getRolesById(rolesId));
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
