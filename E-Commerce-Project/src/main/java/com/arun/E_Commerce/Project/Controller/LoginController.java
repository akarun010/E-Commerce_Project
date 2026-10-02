package com.arun.E_Commerce.Project.Controller;

import com.arun.E_Commerce.Project.Model.Login;
import com.arun.E_Commerce.Project.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @PostMapping("/login")
    public String loggedIn(@RequestBody Login login){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtService.generateToken(login.getEmail());
    }
}
