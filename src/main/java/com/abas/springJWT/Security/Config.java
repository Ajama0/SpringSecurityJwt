package com.abas.springJWT.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
public class Config {

    /**
     * the authentication provider is what authenticates the user once we set up the auth object
     * the jwt only validates the token, and checks that the subject exists in the database
     * the rest is handled by the authentication provider
     * setting supports to true allows jwtfilter to hand authentication over to the auth provider
     * this is because our custom auth provider will be used
     */

    @Bean
    public AuthenticationProvider authenticationProvider(){

    }
}
