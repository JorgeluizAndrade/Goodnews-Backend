package com.project.goodnews.domain.entity.posts;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.goodnews.api.dto.PostRequestDTO;
import com.project.goodnews.domain.entity.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "posts")
@Entity(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@NotBlank(message = "The field name cannot be blank")
	private String title;
	
	@NotBlank(message = "The field name cannot be blank")
	@Column(columnDefinition="text", length=10485760)
	private String text;

	private String slug;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	public Post(PostRequestDTO data) {
		this.title = data.title();
		this.text = data.text();
		this.user = data.user();
	}
}
