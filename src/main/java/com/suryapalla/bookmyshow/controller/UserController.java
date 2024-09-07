package com.suryapalla.bookmyshow.controller;

import com.suryapalla.bookmyshow.dto.UserDetailsResponseDTO;
import com.suryapalla.bookmyshow.dto.UserLoginRequestDTO;
import com.suryapalla.bookmyshow.dto.UserRegistrationRequestDTO;
import com.suryapalla.bookmyshow.model.User;
import com.suryapalla.bookmyshow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody UserRegistrationRequestDTO user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequestDTO user) {
//        return "Hello";
        String key = userService.verifyUser(user);
        return ResponseEntity.ok().body("{\"key\": \""+ key +"\" }");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsResponseDTO> getUserProfile(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok().body(userService.getUserProfile(user.getUsername()));
    }

}
