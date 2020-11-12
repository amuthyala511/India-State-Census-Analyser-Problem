package com.blz.censusanalyser;

import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

public interface ICommonCsvBuilder {
	List<CSVRecord> getCsvFileList(Reader reader);
}
