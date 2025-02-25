package com.project.goodnews.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.goodnews.api.dto.AuthenticationDTO;
import com.project.goodnews.api.dto.LoginResponseDTO;
import com.project.goodnews.api.dto.RegisterDTO;
import com.project.goodnews.api.dto.UserResponseDto;
import com.project.goodnews.domain.entity.user.User;
import com.project.goodnews.infrastructure.TokenService;
import com.project.goodnews.mapper.UserMapper;
import com.project.goodnews.repository.UserRepository;
import com.project.goodnews.service.ipml.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final UserServiceImpl userService;
	private final TokenService tokenService;
	private final UserMapper mapper;

	public AuthController(AuthenticationManager authenticationManager, UserServiceImpl userService,
			TokenService tokenService, UserMapper mapper) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.tokenService = tokenService;
		this.mapper = mapper;
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		// User newUser = new User(data.name(), data.lastname(), data.email(),
		// data.password(), data.role());

		User newUser = mapper.toEntity(data);
		User createdUser = userService.createUser(newUser);

		UserResponseDto response = mapper.toDto(createdUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
