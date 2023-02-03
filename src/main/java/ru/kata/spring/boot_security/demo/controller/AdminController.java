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

    private final UserService adminService;
    private final RoleService roleService;

    public AdminController(UserService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", adminService.getUser(id));
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        adminService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable(name = "id") int id) {
        adminService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "id") int id) {

        ModelAndView modelAndView = new ModelAndView("edit");
        User user = adminService.getUser(id);

        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        adminService.delete(id);
        return "redirect:/admin";
    }
    @GetMapping("/myInfo")
    public String showUserInfo(Principal principal, Model model) {
        model.addAttribute("user", adminService.loadUserByUsername(principal.getName()));
        return "userInfo";
    }
}
