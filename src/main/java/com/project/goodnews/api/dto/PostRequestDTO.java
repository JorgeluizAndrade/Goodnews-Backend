package com.project.goodnews.api.dto;

import com.project.goodnews.domain.entity.user.User;

public record PostRequestDTO(String title, String text, String slug, User user) {
}
