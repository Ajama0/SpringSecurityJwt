package com.abas.springJWT.Security.Controller;


import com.abas.springJWT.Security.AuthResponse;
import com.abas.springJWT.Security.AuthenticationRequest;
import com.abas.springJWT.Security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Auth")
public class Authentication {

    @PostMapping(path = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){

        return new ResponseEntity<>(new AuthResponse(token),HttpStatus.CREATED);

    }




    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest request){



    }
}
