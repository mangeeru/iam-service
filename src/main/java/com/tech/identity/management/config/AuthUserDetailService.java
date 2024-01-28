package com.tech.identity.management.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tech.identity.management.auth.db.entity.UserInfo;
import com.tech.identity.management.auth.db.repository.UserRepository;

@Component
public class AuthUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> credential = Optional.ofNullable(repository.findByUserName(username));
        return credential.map(AuthUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with :" + username));
	}

}
