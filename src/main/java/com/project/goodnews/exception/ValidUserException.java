package com.project.goodnews.exception;

public class ValidUserException extends RuntimeException{
	public ValidUserException(String message) {
		super(message);
	}
	
	public ValidUserException(String mensagem, Object... params) {
		super(String.format(mensagem, params));
	}
}
