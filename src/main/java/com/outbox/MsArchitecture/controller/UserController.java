package com.outbox.MsArchitecture.controller;

import com.outbox.MsArchitecture.dto.UserRequestDto;
import com.outbox.MsArchitecture.entity.User;
import com.outbox.MsArchitecture.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

}