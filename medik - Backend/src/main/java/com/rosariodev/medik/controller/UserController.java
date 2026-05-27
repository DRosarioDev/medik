package com.rosariodev.medik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosariodev.medik.model.dto.UserDTO;
import com.rosariodev.medik.model.dto.UserLoginDTO;
import com.rosariodev.medik.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/utenti/login")
    public UserDTO login(@RequestBody UserLoginDTO userLoginDTO){
        return this.userService.login(userLoginDTO);
    }

}
