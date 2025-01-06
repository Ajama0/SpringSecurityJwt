package com.abas.springJWT.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/v1/demo-controller")
public class Controller {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello from secured endpoint");
    }
}
