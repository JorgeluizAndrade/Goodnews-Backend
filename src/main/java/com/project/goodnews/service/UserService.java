package com.project.goodnews.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.goodnews.api.dto.UpdatdeUserDTO;
import com.project.goodnews.domain.entity.user.User;
import com.project.goodnews.exception.NotFoundException;
import com.project.goodnews.exception.UserAlreadyExistsException;
import com.project.goodnews.exception.ValidUserException;
import com.project.goodnews.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CheckEmailCorrectly checkEmailCorrectly;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();

		Iterable<User> allUsers = this.userRepository.findAll();

		for (User user : allUsers) {
			users.add(user);
		}

		return users;
	}

	public Optional<User> getByUserId(String id) {
		return userRepository.findById(id);
	}

	public User createUser(User data) {
		if (this.userRepository.findByEmail(data.getEmail()) != null) {
			throw new UserAlreadyExistsException("User email already exists.");
		}

		validateUser(data);

		String encryptedPassword = passwordEncoder.encode(data.getPassword());

		User newUser = new User(data.getName(), data.getLastname(), data.getEmail(), encryptedPassword, data.getRole());

		return this.userRepository.save(newUser);
	}

	public User updateUser(String userId, UpdatdeUserDTO data) {

		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ValidUserException("User not found with id: " + userId));

		if (data.name() != null) {
			existingUser.setName(data.name());
		}
		if (data.lastname() != null) {
			existingUser.setLastname(data.lastname());
		}
		if (data.email() != null) {
			if (!checkEmailCorrectly.isEmailValid(data.email())) {
				throw new ValidUserException("Email is not valid. Try with another email.");
			}
			existingUser.setEmail(data.email());
		}
		if (data.password() != null && !data.password().isEmpty()) {
			String encryptedPassword = passwordEncoder.encode(data.password());
			existingUser.setPassword(encryptedPassword);
		}
		if (data.role() != null) {
			existingUser.setRole(data.role());
		}

		return userRepository.save(existingUser);
	}

	public void deleteUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id not exist"));
		userRepository.delete(user);
	}

	public void validateUser(User user) {
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new ValidUserException("Name is required");
		}
		if (user.getLastname() == null || user.getLastname().isEmpty()) {
			throw new ValidUserException("Last name is required");
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new ValidUserException("Email is required");
		}
		if (!checkEmailCorrectly.isEmailValid(user.getEmail())) {
			throw new ValidUserException("Email is not valid. Try with another email.");
		}
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new ValidUserException("Password is required");
		}
	}

}
