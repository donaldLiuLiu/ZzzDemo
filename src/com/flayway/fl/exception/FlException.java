package com.flayway.fl.exception;

public class FlException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String id;
	public FlException(String id, String message) {
		super(message);
		this.setId(id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
