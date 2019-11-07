package com.danielqueiroz.exception;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;



import javassist.tools.reflect.Reflection;

public class QueryProcessorException extends Exception {

	String message;

	public QueryProcessorException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public static void main(String[] args) {
		File file = new File("src\\main\\resources\\models\\pt-token.bin");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.exists());
	}
	
}
