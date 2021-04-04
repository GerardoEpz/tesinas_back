package com.tesinas.spring.jwt.mongodb.payload.response;

public class MessageResponse {
	private String message;
	private Boolean added;

	public MessageResponse(String message,Boolean added) {
	    this.added = added;
		this.message = message;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getAdded() { return added; }

	public void setAdded(Boolean added) { this.added = added; }
}
