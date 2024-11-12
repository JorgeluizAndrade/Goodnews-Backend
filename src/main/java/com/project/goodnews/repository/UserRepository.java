package com.project.goodnews.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.goodnews.domain.entity.user.User;

public interface UserRepository extends JpaRepository<User, String>{
	 User findByEmail(String email);
	
}
