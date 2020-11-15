package com.blz.censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder<E> implements ICSVBuilder {

	public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
		return this.getCSVBean(reader, csvClass).iterator();
	}
	
	@Override
	public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
		return getCSVBean(reader, csvClass).parse();
	}
	
	public CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			return csvToBeanBuilder.build();
		} catch (RuntimeException e) {
			throw new CSVBuilderException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
	}
}
