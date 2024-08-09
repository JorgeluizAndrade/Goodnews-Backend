package com.project.goodnews.api.dto;

import com.project.goodnews.domain.entity.user.UserRole;

public record UpdatdeUserDTO(String name, String lastname, String email, String password, UserRole role) {

}
