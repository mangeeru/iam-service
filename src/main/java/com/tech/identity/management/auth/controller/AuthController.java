package com.tech.identity.management.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.identity.management.auth.db.service.UserService;
import com.tech.identity.management.web.dto.AuthRequest;
import com.tech.identity.management.web.dto.TokenRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
    public String addNewUser(@RequestBody AuthRequest user) {
        return userService.saveUser(user);
    }

    @PostMapping("/get-token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return userService.generateToken(authRequest.getUserName());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate-token")
    public String validateToken(@RequestBody TokenRequest tokenRequest) {
    	boolean flag = userService.validateToken(tokenRequest.getToken(),tokenRequest.getUserName());
        if(flag) {
        	return "true";
        }
    	return "false";
    }

}
