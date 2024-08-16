package com.project.goodnews.utils;

import com.project.goodnews.api.dto.UpdatdeUserDTO;
import com.project.goodnews.domain.entity.user.User;
import com.project.goodnews.exception.ValidUserException;

public class UserUtils {
	public static void validateUser(User user) {
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new ValidUserException("Name is required");
		}
		if (user.getLastname() == null || user.getLastname().isEmpty()) {
			throw new ValidUserException("Last name is required");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new ValidUserException("Email is required");
		}
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new ValidUserException("Password is required");
		}
	}

	public static void validateUpdateUser(String userId, UpdatdeUserDTO data, User existingUser) {
		if (data.name() != null) {
			existingUser.setName(data.name());
		}
		if (data.lastname() != null) {
			existingUser.setLastname(data.lastname());
		}
		if (data.email() != null) {
			existingUser.setEmail(data.email());
		}
		if (data.role() != null) {
			existingUser.setRole(data.role());
		}
	}
}
