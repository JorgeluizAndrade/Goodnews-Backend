package com.project.goodnews.mapper;


import org.springframework.stereotype.Component;

import com.project.goodnews.api.dto.RegisterDTO;
import com.project.goodnews.api.dto.UserResponseDto;
import com.project.goodnews.domain.entity.user.User;

@Component
public class UserMapper {

	public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
            );

	}
	
	public User toEntity(RegisterDTO dto) {
		User user = new User();
	    user.setName(dto.name());  
	    user.setLastname(dto.lastname());
	    user.setEmail(dto.email());
	    user.setPassword(dto.password());  
	    user.setRole(dto.role());  
	    return user;
	}
}
