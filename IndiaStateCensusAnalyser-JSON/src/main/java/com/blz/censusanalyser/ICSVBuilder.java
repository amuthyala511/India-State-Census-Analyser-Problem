package com.blz.censusanalyser;

import java.io.Reader;
import java.util.List;

public interface ICSVBuilder<E> {
	public <E> List<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CSVBuilderException;
}
