package com.blz.censusanalyser;

public class CSVBuilderFactoy {
	public static ICSVBuilder createCsvBuilder() {
		return new CommonCSVBuilder();
	}
}
