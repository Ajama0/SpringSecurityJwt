package com.abas.springJWT.Security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    /**
     * the authentication provider is what authenticates the user once we set up the auth object
     * the jwt only validates the token, and checks that the subject exists in the database
     * the rest is handled by the authentication provider
     * setting supports to true allows jwtfilter to hand authentication over to the auth provider
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
