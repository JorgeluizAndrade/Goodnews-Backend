package com.project.goodnews.exception;

public class ValidPostException extends RuntimeException {
	public ValidPostException(String message) {
		super(message);
	}

	public ValidPostException(String mensagem, Object... params) {
		super(String.format(mensagem, params));
	}

}
