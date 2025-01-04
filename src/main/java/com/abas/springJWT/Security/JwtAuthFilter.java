package com.abas.springJWT.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final CustomUserDetailsService customUserDetailsService;

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
        final String userEmail;
        //now we access the value associated to the authorization part of the header, which is the token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
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
        //extract the payload information (subject-username) from the jwt itself
        userEmail = jwtService.extractUserNames(jwt);

        /**
         * as every subsequent request we re-authenticate the user and set up the context
         * we will check that the jwt includes a subject and the user is not authenticated
         */

        if (userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //we will provide the user with an authentication object by checking the user exists in the db
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)){
                //if the user is valid and the token is valid
                //we create an authentication object in order we can set and update the security context holder
                UsernamePasswordAuthenticationToken authObj = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //essentially we are saying here that the auth object is associated to this request
                authObj.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //set the context to the authentication object that we got from the token
                SecurityContextHolder.getContext().setAuthentication(authObj);
            }

            //pass the request and response to the next set of filters
            filterChain.doFilter(request,response);

    }



    }
}
