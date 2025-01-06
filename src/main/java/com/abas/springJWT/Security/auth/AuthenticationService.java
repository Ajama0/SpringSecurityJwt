package com.abas.springJWT.Security.auth;

import com.abas.springJWT.Security.JwtService;
import com.abas.springJWT.user.Role;
import com.abas.springJWT.user.User;
import com.abas.springJWT.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getFirstname(),
                request.getLastname(),
                request.getEmail()
        );
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        //need to generate a jwt token for the user as the user has just registerd
        //because the user object is of type userdetails, any instance of the user is of type userdetails
        // the user object will only be able to access the functions within the userdetails class
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    /**
     * when we call the authenticate method, spring will invoke the implementation of the
     * authenticate method in the providerManager which allows us to pass the userobject to the dao
     * the dao

     */
    public AuthResponse authenticate(AuthenticationRequest request) {
        //this trhows an exception if authenticate fails
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));

        //if we reach here the user has been authenticated as he exists in the db
        UserDetails user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("user does not exist"));
        //we generate a token for the user and use the email to set as the claims subject
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);

    }
}
