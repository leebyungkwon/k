package com.skm.sso.config.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ExceptionCustom.class)
	public ResponseEntity<Map<String, Object>> handler(ExceptionCustom e , HttpHeaders header, HttpStatus status) {
		Map<String, Object> resBody = new HashMap<>();
		resBody.put("status", e.getStatus());
		resBody.put("message", e.getMessage());
		resBody.put("time", new Date().toString());
		
		System.out.println(new Date().toString() + " ### @ExceptionHandler :: " + e.getStatus() + " , " + e.getMessage());
		return new ResponseEntity<>(resBody, e.getStatus());
	}
	
}