package com.project.goodnews.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.slugify.Slugify;
import com.project.goodnews.api.dto.PostRequestDTO;
import com.project.goodnews.domain.entity.posts.Post;
import com.project.goodnews.domain.entity.user.User;
import com.project.goodnews.exception.NotFoundException;
import com.project.goodnews.exception.ValidPostException;
import com.project.goodnews.exception.ValidUserException;
import com.project.goodnews.repository.PostRepository;
import com.project.goodnews.repository.UserRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<>();

		Iterable<Post> postsAll = this.postRepository.findAll();

		for (Post post : postsAll) {
			posts.add(post);
		}
		return posts;
	}

	public Optional<Post> getByPostSlugAndId(String slug, String id) {
		return postRepository.findBySlugAndId(slug, id);
	}

	public Post createPost(PostRequestDTO data) {

		final Slugify slg = Slugify.builder().build();
		String slugData = slg.slugify(data.title());

		if (data.slug() != null && !data.slug().isEmpty())
			slugData = data.slug();

		Post newPost = new Post(data);

		newPost.setSlug(slugData);

		validatePost(newPost);

		return postRepository.save(newPost);
	}

	public void deletePost(String postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
		postRepository.delete(post);
	}

	public Post updatePost(String postId, PostRequestDTO data) {

		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new ValidUserException("Post not found with id: " + postId));

		final Slugify slg = Slugify.builder().build();
		String slugData = slg.slugify(data.title());

		if (data.slug() != null && !data.slug().isEmpty()) {
			slugData = data.slug();
			existingPost.setSlug(slugData);
		}

		if (data.title() != null && !data.title().isEmpty()) {
			existingPost.setTitle(data.title());
		}

		if (data.text() != null && !data.text().isEmpty()) {
			existingPost.setText(data.text());
		}

		if (data.user().getId() == null && data.user().getId().isEmpty()) {
			throw new ValidPostException("User Id is required");
		}

		if (!userRepository.existsById(existingPost.getUser().getId())) {
			throw new ValidPostException("User Id not exist. try another id");
		}

		return postRepository.save(existingPost);
	}

	public void validatePost(Post post) {
		if (post.getTitle() == null || post.getTitle().isEmpty()) {
			throw new ValidPostException("Title is required");
		}
		if (post.getText() == null || post.getText().isEmpty()) {
			throw new ValidPostException("Text is required");
		}
		if (post.getUser().getId() == null || post.getUser().getId().isEmpty()) {
			throw new ValidPostException("User Id is required");
		}
		if (!userRepository.existsById(post.getUser().getId())) {
			throw new ValidPostException("User Id not exist. try another id");
		}
	}

}
