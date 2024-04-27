package com.tech.identity.management.auth.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tech.identity.management.auth.db.entity.UserInfo;
import com.tech.identity.management.web.dto.AuthRequest;

@Mapper
public interface UserMapper{
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mapping(source = "dateOfBirth",target = "dob")
	UserInfo authUserToInfo(AuthRequest request);

}
