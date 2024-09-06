package com.suryapalla.bookmyshow.service;

import com.suryapalla.bookmyshow.dto.UserDetailsResponseDTO;
import com.suryapalla.bookmyshow.dto.UserLoginRequestDTO;
import com.suryapalla.bookmyshow.dto.UserRegistrationRequestDTO;
import com.suryapalla.bookmyshow.model.User;
import com.suryapalla.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public User createUser(UserRegistrationRequestDTO userRequest) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        user.setTickets(new ArrayList<>());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public String verifyUser(UserLoginRequestDTO user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        }
        System.out.println("Invalid username or password");
        return "Fail";
    }

    public UserDetailsResponseDTO getUserProfile(String userEmail) {
        User userFromDataBase = userRepository.findUserByEmail(userEmail);
        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO();
        userDetailsResponseDTO.setEmail(userEmail);
        userDetailsResponseDTO.setRole(userFromDataBase.getRole().toString());
        userDetailsResponseDTO.setUsername(userFromDataBase.getName());
        userDetailsResponseDTO.setTickets(userFromDataBase.getTickets());
        return userDetailsResponseDTO;
    }

}
