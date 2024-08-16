package com.project.goodnews.utils;

import com.github.slugify.Slugify;
import com.project.goodnews.api.dto.PostRequestDTO;
import com.project.goodnews.domain.entity.posts.Post;
import com.project.goodnews.exception.ValidPostException;

public class PostUtils {
	public static void validatePost(Post post) {
		if (post.getTitle() == null || post.getTitle().isEmpty()) {
			throw new ValidPostException("Title is required");
		}
		if (post.getText() == null || post.getText().isEmpty()) {
			throw new ValidPostException("Text is required");
		}
		if (post.getUser().getId() == null || post.getUser().getId().isEmpty()) {
			throw new ValidPostException("User Id is required");
		}
	}

	public static void validateUpadatePost(PostRequestDTO data, Post existingPost, String userId) {
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
		if (!userId.equals(existingPost.getUser().getId())) {
			throw new ValidPostException("User Id is wrong. Try with correct ID");
		}

		if (!userId.equals(data.user().getId())) {
			throw new ValidPostException("User Id is wrong!");
		}
	}
}
