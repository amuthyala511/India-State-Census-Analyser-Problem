package com.blz.censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;

public class CensusAnalyser {
	static int numOfRecords = 0;
	List<IndiaCensusCSV> indiaCensusCSVList = null;
	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactoy.createCsvBuilder();
			indiaCensusCSVList = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
			return indiaCensusCSVList.size();
		} catch (NoSuchFileException e) {
			if (!csvFilePath.contains(".csv")) {
				throw new CensusAnalyserException(e.getMessage(),
						CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		return numOfRecords;
	}

	
	public int loadIndiaCodeData(String csvFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactoy.createCsvBuilder();
			indiaCensusCSVList = csvBuilder.getCSVFileIterator(reader, IndiaCodeCSV.class);
			return indiaCensusCSVList.size();
		} catch (NoSuchFileException e) {
			if (!csvFilePath.contains(".csv")) {
				throw new CensusAnalyserException(e.getMessage(),
						CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
			}
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (CSVBuilderException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		return numOfRecords;
	}
	
	
	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfRecords = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfRecords;
	}
	
	public String getStateWiseSortedCensusData(String filePath) throws CensusAnalyserException {
		loadIndiaCensusData(filePath);
		if(indiaCensusCSVList == null || indiaCensusCSVList.size() == 0) {
			throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(censusComparator);
		String sortedStateCensusAsJSON = new Gson().toJson(indiaCensusCSVList);
		return sortedStateCensusAsJSON;
	}
	
	public void sort(Comparator<IndiaCensusCSV> censusCSVComparator) {
		for(int index = 0; index < indiaCensusCSVList.size() - 1; index++) {
            for(int index2 = 0; index2 < indiaCensusCSVList.size() - index - 1; index2++) {
                IndiaCensusCSV census1 = indiaCensusCSVList.get(index2);
                IndiaCensusCSV census2 = indiaCensusCSVList.get(index2 + 1);
                if(censusCSVComparator.compare(census1, census2) > 0) {
                    indiaCensusCSVList.set(index2, census2);
                    indiaCensusCSVList.set(index2 + 1, census1);
                }
            }
        }
	}
}

