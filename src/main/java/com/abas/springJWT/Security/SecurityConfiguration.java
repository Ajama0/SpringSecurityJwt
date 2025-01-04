package com.abas.springJWT.Security;

import com.abas.springJWT.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomAuthenticationProvider authenticationProvider;
    /**
     * At startup springs IOC searches for all beans(@Service, @Component)
     * whenever these beans are spotten, spring instantiates them as a singleton and provides its reference to other beans that may require it
     * method level beans allow spring to return the instance of the method and invoke it
     * spring will then allow other classes who require the instance to use it
     * spring at startup requires a securityFilterChain, hence this method is invoked
     *
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {



        /**
         * Use lambdas on deprecated functions
         * ensure our state management is stateless as jwt's are stateless(checked on every request)
         * provide the authentication provider
         * ensure our JwtFilter gets used before the UsernamePasswordAuthtoken
         *
         */
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/login").
                        permitAll()
                        .anyRequest().
                        authenticated()
                )
                        .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)


                )
                .authenticationProvider(authentication)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)




    }




}
