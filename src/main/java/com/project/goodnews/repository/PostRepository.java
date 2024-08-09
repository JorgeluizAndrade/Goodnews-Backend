package com.project.goodnews.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.goodnews.domain.entity.posts.Post;

public interface PostRepository extends JpaRepository<Post, String>{	
	Optional<Post> findBySlugAndId(String slug, String id);
	
	Optional<Post> findById(String id);
}
