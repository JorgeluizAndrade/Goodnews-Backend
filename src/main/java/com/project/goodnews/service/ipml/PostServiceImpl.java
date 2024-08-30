package com.project.goodnews.service.ipml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.slugify.Slugify;
import com.project.goodnews.api.dto.PostRequestDTO;
import com.project.goodnews.domain.entity.posts.Post;
import com.project.goodnews.exception.NotFoundException;
import com.project.goodnews.exception.ValidPostException;
import com.project.goodnews.exception.ValidUserException;
import com.project.goodnews.repository.PostRepository;
import com.project.goodnews.repository.UserRepository;
import com.project.goodnews.service.PostService;
import com.project.goodnews.utils.PostUtils;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Post> getAllPosts() {
		return this.postRepository.findAll();		
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

		PostUtils.validatePost(newPost);

		if (!userRepository.existsById(data.user().getId())) {
			throw new ValidPostException("User Id not exist. try another id");
		}

		return postRepository.save(newPost);
	}

	public void deletePost(String postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
		postRepository.delete(post);
	}

	public Post updatePost(String postId, String userId, PostRequestDTO data) {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new ValidUserException("Post not found with id: " + postId));

		PostUtils.validateUpadatePost(data, existingPost, userId);

		if (!userRepository.existsById(existingPost.getUser().getId())) {
			throw new ValidPostException("User Id not exist. try another id");
		}

		return postRepository.save(existingPost);
	}
}
