package com.skm.sso.config.exception;

import org.springframework.http.HttpStatus;

public class ExceptionCustom extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	private static String message;
    
    public ExceptionCustom(HttpStatus status, String code){
        super(getMsg(code) + " errCode : [" + code + "]");
        this.status = status;
		this.message = getMsg(code);
    }

    public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}
	
	private static String getMsg(String errCode) {
    	switch(errCode) {
	        case "P0001": message = ExceptionConst.P0001;
	             break;
	        case "M0001": message = ExceptionConst.M0001;
	        	break;
	        case "M0002": message = ExceptionConst.M0002;
        		break;
	        case "M0003": message = ExceptionConst.M0003;
        		break;
	        case "S0001": message = ExceptionConst.S0001;
        		break;
	        case "S0002": message = ExceptionConst.S0002;
    			break;
	        case "D0001": message = ExceptionConst.D0001;
        		break;
	        default: message = ExceptionConst.DEFAULT;
	             break;
	    }
    	return message;
    }
}


