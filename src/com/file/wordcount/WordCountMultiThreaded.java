package com.file.wordcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class counts the occurrence of the words from a text file using
 * multithreading
 * 
 * @author Sushama Gaddam
 *
 */
public class WordCountMultiThreaded {
	private static final String FILE_URL = "https://www.gnu.org/licenses/gpl-3.0.txt";
	private static int THREAD_COUNT = 5;
	private static final String REGEXP = "[^a-zA-Z]";

	public static void main(String[] args) {
		processWordCountGeneration();
	}
	
	
	/**
	 * Method to read text file and process word count occurrence count calculation 
	 */
	private static void processWordCountGeneration() {
		Map<String, Integer> wordCountMap = new HashMap<>();
		List<String> lineList = new ArrayList<>();
		Thread[] threads = new Thread[THREAD_COUNT];
		List<List<String>> sublists = new ArrayList<>();
		try {
			// Read the file from given URL
			URL oracle = new URL(FILE_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String inputLine;
			// Code to read each line from the file
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.trim().length() != 0)
					lineList.add(inputLine);
			}

			in.close();

			// Invoke method for populating the word count
			setIndexRangeForThread(lineList, sublists);

			// Invoke method for triggering multi-threading
			processMultiThreadWordCount(sublists, threads, wordCountMap);

			printWordCountValues(wordCountMap);
		} catch (IOException e) {
			e.getMessage();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method splits the total count of lines from the file to sublists where
	 * each sublist is processed by single different thread for finding the word
	 * count.
	 * 
	 * @param lineList
	 * @param sublists
	 */
	public static void setIndexRangeForThread(List<String> lineList, List<List<String>> sublists) {
		int startIndex = 0;
		int lineCount = lineList.size();
		int totalSplits = lineList.size() / THREAD_COUNT;
		int reminderValues = lineList.size() % THREAD_COUNT;

		if (totalSplits == 0) {
			List<String> sublist = lineList.subList(startIndex, reminderValues);
			sublists.add(sublist);
		} else {
			while (startIndex < lineCount) {
				int toIndex = 0;
				if (sublists.size() == THREAD_COUNT - 1)
					toIndex = (sublists.size() * totalSplits) + totalSplits + reminderValues;
				else
					toIndex = (sublists.size() * totalSplits) + totalSplits;
				List<String> sublist = lineList.subList(startIndex, toIndex);
				sublists.add(sublist);
				startIndex = toIndex;

			}
		}

	}

	/**
	 * This method initiates threads, where each thread process different sublist
	 * and builds local map with corresponding word count and then merges it with
	 * final word count map resulting in word count from all the threads.
	 * 
	 * @param sublists
	 * @param threads
	 * @param wordCountMap
	 * @throws InterruptedException
	 */
	public static void processMultiThreadWordCount(List<List<String>> sublists, Thread[] threads,
			Map<String, Integer> wordCountMap) throws InterruptedException {
		THREAD_COUNT = THREAD_COUNT > sublists.size() ? sublists.size() : THREAD_COUNT;
		for (int i = 0; i < THREAD_COUNT; i++) {
			int index = i;
			threads[i] = new Thread(() -> {
				Map<String, Integer> localWordCountMap = new HashMap<>();
				buildWordCount(sublists.get(index), localWordCountMap);
				mergeWordCountMaps(wordCountMap, localWordCountMap);
			});
			threads[i].start();
		}

		// Wait for all threads to finish
		for (Thread thread : threads) {
			thread.join();
		}

	}

	/**
	 * This method invokes populateWordCount by passing single line from list of
	 * lines.
	 * 
	 * @param lineList
	 * @param localWordCountMap
	 */
	public static void buildWordCount(List<String> lineList, Map<String, Integer> localWordCountMap) {
		for (String line : lineList)
			buildWordCountMap(line, localWordCountMap);
	}

	/**
	 * This method builds map having words as keys and value as its occurrence count
	 * of each line from the file
	 * 
	 * @param line
	 * @param wordCountMap
	 */
	public static void buildWordCountMap(String line, Map<String, Integer> wordCountMap) {
		// Split the text and remove any trailing spaces
		for (String str : line.toLowerCase().replaceAll(REGEXP, " ").trim().split("\\s")) {
			if (str.trim().length() != 0)
				wordCountMap.put(str.trim(), wordCountMap.getOrDefault(str.trim(), 0) + 1);
		}
	}

	/**
	 * This method merges the word counts maps generated from distinct threads into
	 * single map.
	 * 
	 * @param wordCountMap
	 * @param localWordCountMap
	 */
	public static void mergeWordCountMaps(Map<String, Integer> wordCountMap, Map<String, Integer> localWordCountMap) {
		for (Map.Entry<String, Integer> entry : localWordCountMap.entrySet()) {
			wordCountMap.put(entry.getKey(), wordCountMap.getOrDefault(entry.getKey(), 0) + entry.getValue());
		}
	}
	
	/**
	 * Method to print map in desired format
	 * @param wordCountMap
	 */
	public static void printWordCountValues(Map<String, Integer> wordCountMap) {
		StringBuilder sb = new StringBuilder("{");
		int count = 0;
		for (String key : wordCountMap.keySet()) {
			sb.append("'").append(key).append("': ").append(wordCountMap.get(key));
			count++;
			if (count < wordCountMap.size()) {
				sb.append(", ");
			}

		}
		sb.append("}");
		System.out.println(sb.toString());
		
	}
}
