package com.blz.censusanalyser;

public class CensusAnalyserException extends Exception {

	enum ExceptionType {
		CENSUS_FILE_PROBLEM, WRONG_FILE_TYPE, NO_SUCH_FILE, NO_SUCH_FIELD, UNABLE_TO_PARSE, NO_CENSUS_DATA
	}

	public ExceptionType type;

	public CensusAnalyserException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

	public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
		super(message);
		this.type = type;
	}
	
	public CensusAnalyserException(String message, String name) {
		super(message);
		this.type = ExceptionType.valueOf(name);
	}
}
