package com.abas.springJWT.Security;

import com.abas.springJWT.user.User;
import com.abas.springJWT.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(){}
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * as user implements userDetails, any instance of users is a reference to userDetails
         * the instance returned by the User object means its of type UserDetails
         * however that object can only be called on methods that are accessible by UserDetails
         * other methods like getFirstName would mean we need to cast the object to a user object
         */

        return userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user does not exist"));
    }
}
