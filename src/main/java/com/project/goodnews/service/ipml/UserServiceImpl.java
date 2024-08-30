package com.project.goodnews.service.ipml;

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
import com.project.goodnews.service.UserService;
import com.project.goodnews.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CheckEmailCorrectlyImpl checkEmailCorrectly;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return this.userRepository.findAll();

	}

	public Optional<User> getByUserId(String id) {
		return userRepository.findById(id);
	}

	public User createUser(User data) {
		if (this.userRepository.findByEmail(data.getEmail()) != null) {
			throw new UserAlreadyExistsException("User email already exists.");
		}
		if (!checkEmailCorrectly.isEmailValid(data.getEmail())) {
			throw new ValidUserException("Email is not valid. Try with another email.");
		}

		UserUtils.validateUser(data);

		String encryptedPassword = passwordEncoder.encode(data.getPassword());

		User newUser = new User(data.getName(), data.getLastname(), data.getEmail(), encryptedPassword, data.getRole());

		return this.userRepository.save(newUser);
	}

	public User updateUser(String userId, UpdatdeUserDTO data) {

		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new ValidUserException("User not found with id: " + userId));

		UserUtils.validateUpdateUser(userId, data, existingUser);

		if (!checkEmailCorrectly.isEmailValid(data.email())) {
			throw new ValidUserException("Email is not valid. Try with another email.");
		}
		if (data.password() != null && !data.password().isEmpty()) {
			String encryptedPassword = passwordEncoder.encode(data.password());
			existingUser.setPassword(encryptedPassword);
		}

		return userRepository.save(existingUser);
	}

	public void deleteUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User id not exist"));
		userRepository.delete(user);
	}
}
