package com.project.goodnews.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class CheckEmailCorrectly {

    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

	
	public boolean isEmailValid(String email) {
		Pattern pattern = Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
}
