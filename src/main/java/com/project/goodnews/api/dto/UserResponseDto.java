package com.project.goodnews.api.dto;

import com.project.goodnews.domain.entity.user.UserRole;

public record UserResponseDto(
	    String id,
	    String username,
	    String email,
	    UserRole role
	) {}
