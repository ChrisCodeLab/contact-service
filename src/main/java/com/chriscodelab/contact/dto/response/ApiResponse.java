package com.chriscodelab.contact.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success", "message", "data", "errors"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	
	private boolean success;
	private String message;
	private T data;
	private List<String> errors;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
		
	public List<String> getErrors() {
		return errors;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	@Override
	public String toString() {
		return "ApiResponse [success=" + success + ", message=" + message + ", data=" + data + ", errors=" + errors
				+ "]";
	}
	
	public ApiResponse() {
		super();
	}
	
	public ApiResponse(boolean success, String message, T data, List<String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.errors = errors;
	}
	
	public ApiResponse(boolean success, String message, T data) {
		this(success, message, data, null);
	}
	
	
}
