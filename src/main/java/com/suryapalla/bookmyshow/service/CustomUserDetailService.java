package com.suryapalla.bookmyshow.service;


import com.suryapalla.bookmyshow.model.User;
import com.suryapalla.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if(user == null){
            System.out.println("Unable to find user with username: "+email);
            throw new UsernameNotFoundException("Unable to find user with username: "+email);
        }
        return new UserPrincipal(user);
    }
}
