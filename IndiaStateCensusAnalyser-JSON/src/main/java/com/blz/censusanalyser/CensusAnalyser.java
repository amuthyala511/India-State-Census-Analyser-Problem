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
	List<IndiaCodeCSV> indiaCodeCSVList = null;
	
	public int loadIndiaCensusData(String csvFilePath)
			throws CensusAnalyserException {
		
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactoy.createCsvBuilder();
			indiaCensusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
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
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		}
		return numOfRecords;
	}

	
	public int loadIndiaCodeData(String csvFilePath)
			throws CensusAnalyserException {
		
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactoy.createCsvBuilder();
			indiaCensusCSVList = csvBuilder.getCSVFileList(reader, IndiaCodeCSV.class);
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
			throw new CensusAnalyserException(e.getMessage(), e.type.name());
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		return numOfRecords;
	}
	
	public String getStateWiseSortedCensusData(String filePath) throws CensusAnalyserException {
		loadIndiaCensusData(filePath);
		if(indiaCensusCSVList == null || indiaCensusCSVList.size() == 0) {
			throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
		this.sort(indiaCensusCSVList, censusComparator);
		String sortedStateCensusAsJSON = new Gson().toJson(indiaCensusCSVList);
		return sortedStateCensusAsJSON;
	}
	
	public String getStateWiseSortedPopulationCensusData(String filePath) throws CensusAnalyserException {
		loadIndiaCensusData(filePath);
		if(indiaCensusCSVList == null || indiaCensusCSVList.size() == 0) {
			throw new CensusAnalyserException("No census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
		this.sort(indiaCensusCSVList, censusComparator);
		String sortedStateCensusAsJSON = new Gson().toJson(indiaCensusCSVList);
		return sortedStateCensusAsJSON;
	}
	
	public void sort(List<IndiaCensusCSV> indiaCensusCSVList, Comparator<IndiaCensusCSV> censusComparator) {
		for(int i = 0; i < indiaCensusCSVList.size(); i++) {
            for(int j = 0; j < indiaCensusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = indiaCensusCSVList.get(j);
                IndiaCensusCSV census2 = indiaCensusCSVList.get(j + 1);
                if(censusComparator.compare(census1, census2) > 0) {
                    indiaCensusCSVList.set(j, census2);
                    indiaCensusCSVList.set(j + 1, census1);
                }
            }
        }
	}
	
	public String getStateCodeWiseSortedCodeData(String filePath) throws CensusAnalyserException {
		loadIndiaCodeData(filePath);
		if(indiaCodeCSVList == null || indiaCodeCSVList.size() ==0) {
			throw new CensusAnalyserException("No Code data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
		}
		Comparator<IndiaCodeCSV> codeComparator = Comparator.comparing(census -> census.stateCode);
		this.codeSort(indiaCodeCSVList, codeComparator);
		String sortedStateCodeAsJSON = new Gson().toJson(indiaCodeCSVList);
		return sortedStateCodeAsJSON;
	}
	
	public void codeSort(List<IndiaCodeCSV> indiaCodeCSVList, Comparator<IndiaCodeCSV> codeComparator) {
		for(int i = 0; i < indiaCodeCSVList.size(); i++) {
			for(int j = 0; j < indiaCodeCSVList.size() - i - 1; j++) {
				IndiaCodeCSV census1 = indiaCodeCSVList.get(j);
				IndiaCodeCSV census2 = indiaCodeCSVList.get(j + 1);
				if(codeComparator.compare(census1, census2) > 0) {
					indiaCodeCSVList.set(j, census2);
					indiaCodeCSVList.set(j + 1, census1);
				}
			}
		}
	}
}

