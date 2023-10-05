package com.poscodx.jblog.exception;

public class FileUploadServiceException extends RuntimeException {

	public FileUploadServiceException(String message) {
		super(message);
	}

	public FileUploadServiceException() {
		super("FileUploadSErvice Exception Thrown");
	}

}
