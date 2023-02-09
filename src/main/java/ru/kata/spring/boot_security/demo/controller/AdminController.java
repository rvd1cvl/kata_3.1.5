package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "adminPage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", userService.getUser(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {
        model.addAttribute("newUser", new User());
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "newUser";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable(name = "id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/editUser/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "id") int id) {

        ModelAndView modelAndView = new ModelAndView("edit");
        User user = userService.getUser(id);

        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @DeleteMapping("/deleteUser/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        userService.delete(id);
        return "deleteUser";
    }
    @GetMapping("/myInfo")
    public String showUserInfo(Principal principal, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "userInfo";
    }
}
