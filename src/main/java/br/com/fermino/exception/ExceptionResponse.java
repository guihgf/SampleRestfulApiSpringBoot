package br.com.fermino.exception;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Guilherme Fermino
 *
 */
public class ExceptionResponse implements Serializable{
	
	private Date timestamp;
	private String message;
	private String details;
	
	
	public ExceptionResponse(java.util.Date date, String message, String details) {
		super();
		this.timestamp = date;
		this.message = message;
		this.details = details;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	
	
	
}
