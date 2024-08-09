package com.project.goodnews.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(String mensagem, Object... params) {
		super(String.format(mensagem, params));
	}

}
