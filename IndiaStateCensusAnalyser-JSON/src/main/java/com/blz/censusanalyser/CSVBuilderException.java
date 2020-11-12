package com.blz.censusanalyser;

public class CSVBuilderException extends Exception {
	enum ExceptionType {
		CENSUS_FILE_PROBLEM, NO_SUCH_FILE, NO_SUCH_FIELD, UNABLE_TO_PARSE
	}
	
	ExceptionType type;

	public CSVBuilderException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

	public CSVBuilderException(String message, ExceptionType type, Throwable cause) {
		super(message);
		this.type = type;
	}
}

