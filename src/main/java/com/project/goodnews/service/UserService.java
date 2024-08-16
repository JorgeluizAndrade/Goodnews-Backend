package com.project.goodnews.service;

import java.util.List;
import java.util.Optional;

import com.project.goodnews.api.dto.UpdatdeUserDTO;
import com.project.goodnews.domain.entity.user.User;

public interface UserService {
	public List<User> getAllUsers();

	public Optional<User> getByUserId(String id);

	public User createUser(User data);

	public User updateUser(String userId, UpdatdeUserDTO data);

	public void deleteUser(String userId);
}
