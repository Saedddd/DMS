package com.example.dms.controller;

import com.example.dms.model.Role;
import com.example.dms.model.User;
import com.example.dms.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.dms.model.Views;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(Views.Basic.class)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return userService.getAllRoles();
    }

    @GetMapping("/{id}")
    @JsonView(Views.Detailed.class)
    public User getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @JsonView(Views.Detailed.class)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable UUID id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}

