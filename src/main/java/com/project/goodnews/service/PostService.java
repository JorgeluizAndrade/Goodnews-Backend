package com.project.goodnews.service;

import java.util.List;
import java.util.Optional;

import com.project.goodnews.api.dto.PostRequestDTO;
import com.project.goodnews.domain.entity.posts.Post;

public interface PostService {
	public List<Post> getAllPosts();

	public Optional<Post> getByPostSlugAndId(String slug, String id);

	public Post createPost(PostRequestDTO data);

	public void deletePost(String postId);

	public Post updatePost(String postId, String userId, PostRequestDTO data);
}
