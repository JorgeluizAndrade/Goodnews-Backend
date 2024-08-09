package com.project.goodnews.exception;

public class NotFoundException extends RuntimeException{
	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String mensagem, Object... params) {
		super(String.format(mensagem, params));
	}
}
