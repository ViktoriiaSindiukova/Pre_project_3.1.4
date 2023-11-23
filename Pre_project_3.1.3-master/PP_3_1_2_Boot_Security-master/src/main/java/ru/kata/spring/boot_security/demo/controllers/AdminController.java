package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entitis.Role;
import ru.kata.spring.boot_security.demo.entitis.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.utill.InvalidDataException;
import ru.kata.spring.boot_security.demo.utill.UserNotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Principal principal, Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("authorizationUser", userService.findByName(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    @PostMapping("/users/{id}")
    public String saveEdit(@PathVariable Long id,
                           @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new InvalidDataException(errorMessage.toString());
        }

        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") @Valid User newUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new InvalidDataException(errorMessage.toString());
        }

        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
