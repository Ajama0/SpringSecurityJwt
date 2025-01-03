package com.abas.springJWT.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        /**
         * the jwt token lies in the authorization header of a request, we need to check once a client sends
         * a request, whether the client has sent a valid jwt,
         * from the request, we can extract the header to see the jwt
         */
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        //now we access the value associated to the authorization part of the header, which is the token
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            /**
             * allow for other operations to still continue with the request
             * what if the endpoint is public like sign up or login, and we threw an exception here?
             * this would mean no other filter checks are done, Cors filter checks etc
             */
            filterChain.doFilter(request, response);
        }

        //if the jwt is present and is valid, we can now access the token
        //to access the token, we need to start from the 7th index in which it removes the word bearer
        jwt = authHeader.substring(7);


        }



    }
}
