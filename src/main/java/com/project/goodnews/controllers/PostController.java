package com.project.goodnews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.goodnews.api.dto.PostRequestDTO;
import com.project.goodnews.domain.entity.posts.Post;
import com.project.goodnews.exception.NotFoundException;
import com.project.goodnews.service.ipml.PostServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
public class PostController {

	@Autowired
	private PostServiceImpl postService;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping
	public List<Post> findAll() {
		return postService.getAllPosts();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/{slug}/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity findBySlugAndId(@PathVariable("slug") String slug, @PathVariable("id") String id) {
		return postService.getByPostSlugAndId(slug, id).map(ResponseEntity::ok)
				.orElseThrow(() -> new NotFoundException("Post not found with the given ID or Slug."));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity createPost(@RequestBody @Valid PostRequestDTO data) {
		Post createdPost = postService.createPost(data);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
	}

	@PutMapping("/{id}/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity attPost(@PathVariable("id") String id, @PathVariable("userId") String userId,
			@RequestBody PostRequestDTO data) {
		Post updatePost = postService.updatePost(id, userId, data);

		return ResponseEntity.status(HttpStatus.OK).body(updatePost);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity delete(@PathVariable("id") String id) {
		try {
			this.postService.deletePost(id);
			return ResponseEntity.ok("Post deleted successfully");
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the post");
		}
	}

}
