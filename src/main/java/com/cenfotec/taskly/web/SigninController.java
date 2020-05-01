package com.cenfotec.taskly.web;

import com.cenfotec.taskly.domain.User;
import com.cenfotec.taskly.service.SigninService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SigninController {
    @Autowired
    SigninService signinService;

    @CrossOrigin
    @PostMapping("/api/signin")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        User newUser = new User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
                user.getNombre(), user.getApellidos(), true);
        signinService.registerUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
