package com.abas.springJWT.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {


    /**
     * create another bean at method level to return an instance of our userdetailservice implementation
     * the method will be used for the authenticaiton provider to validate our user in the db
     * the authprovider internally calls the loadUserByUsername()
     *
     */

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService();
    }



    /**
     * the authentication provider is what authenticates the user once we set up the auth object
     * the jwt only validates the token, and checks that the subject exists in the database
     * the rest is handled by the authentication provider
     * The Bean annotation allows spring to invoke the method and return its instance
     * the instance returned will be injected into any component that requires it
     *
     */


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());



    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
