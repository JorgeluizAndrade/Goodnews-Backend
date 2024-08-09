package com.project.goodnews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.project.goodnews.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping
	public List<Post> findAll() {
		return postService.getAllPosts();
	}

	@GetMapping("/{slug}/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity findBySlugAndId(@PathVariable("slug") String slug, @PathVariable("id") String id) {
		return postService.getByPostSlugAndId(slug, id).map(ResponseEntity::ok)
				.orElseThrow(() -> new NotFoundException("Post not found with the given ID or Slug."));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity createPost(@RequestBody @Valid PostRequestDTO data) {
		Post createdPost = postService.createPost(data);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity attPost(@RequestBody PostRequestDTO data) {
		Post updatePost = postService.updatePost(data);

		return ResponseEntity.status(HttpStatus.OK).body(updatePost);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
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
