package com.abas.springJWT.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Config {


    /**
     *as the set userdetailsservice requires a instance of customuserdetails service
     * we do not create another bean of userdetailsservice as it would lead to bean conflicts
     * instead we can inject our ready made bean and pass that in
     *
     */

   private final UserDetailsService userDetailsService;


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
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     *returns an instance of type authentication manager with a providerManager implementation
     * essentially a providerManager instance, however we can only call the functions in the interface
     * the method gets invoked and the return type of the method gets used as a singleton bean
     * this bean which is an impl of provider manager can be used.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    //returns
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
