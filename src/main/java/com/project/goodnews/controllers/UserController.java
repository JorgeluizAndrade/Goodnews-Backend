package com.project.goodnews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.goodnews.api.dto.UpdatdeUserDTO;
import com.project.goodnews.domain.entity.user.User;
import com.project.goodnews.exception.NotFoundException;
import com.project.goodnews.request.payload.ApiResponse;
import com.project.goodnews.service.ipml.UserServiceImpl;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping
	public List<User> findAll() {
		return this.userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable("id") String id) {
		return userService.getByUserId(id).map(ResponseEntity::ok)
				.orElseThrow(() -> new NotFoundException("User not found with the given ID."));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<User> attUser(@PathVariable String id, @RequestBody UpdatdeUserDTO data) {
		User updateUser = userService.updateUser(id, data);

		return ResponseEntity.status(HttpStatus.OK).body(updateUser);
	}

	@PutMapping("/{email}/giveAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> giveAdmin(@PathVariable(name = "email") String email) {
		ApiResponse apiResponse = userService.giveAdmin(email);

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity deleteUser(@PathVariable("id") String id) {
		try {
			this.userService.deleteUser(id);
			return ResponseEntity.ok("User deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the user");
		}

	}
}
