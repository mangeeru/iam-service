package com.tech.identity.management.auth.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.identity.management.auth.db.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>{
	
	UserInfo findByUserName(String userName);

}
