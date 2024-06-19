package com.tech.identity.management.auth.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.identity.management.auth.db.entity.UserInfo;
import com.tech.identity.management.auth.db.repository.UserRepository;
import com.tech.identity.management.auth.util.JwtUtil;
import com.tech.identity.management.auth.util.mapper.UserMapper;
import com.tech.identity.management.web.dto.AuthRequest;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String saveUser(AuthRequest authRequest) {
		//UserInfo userInfo = UserMapper.INSTANCE.authUserToInfo(authRequest);
		UserInfo userInfo = new UserInfo(authRequest.getUserName(), authRequest.getEmail(), authRequest.getPhone());
		userInfo.setSecrethash(passwordEncoder.encode(authRequest.getPassword()));
        userRepository.save(userInfo);
        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public boolean validateToken(String token, String username) {
    	return jwtUtil.validateToken(token,username);
    }

}
