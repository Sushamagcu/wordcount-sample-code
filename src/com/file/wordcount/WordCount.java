package com.file.wordcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class counts the occurrence of the words from a text file
 * 
 * @author sushama Gaddam
 *
 */
public class WordCount {

	private static final String FILE_URL = "https://www.gnu.org/licenses/gpl-3.0.txt";
	private static final String REGEXP = "[^a-zA-Z]";

	public static void main(String[] args) {
		processWordCountGeneration();
	}

	/**
	 * Method to read text file and process word count occurrence count calculation 
	 */
	private static void processWordCountGeneration() {
		Map<String, Integer> wordCountMap = new HashMap<>();
		try {
			// Read the file from given URL
			URL oracle = new URL(FILE_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			// Read file till EOF and remove all special characters, digits and trailing spaces
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.trim().length() != 0) {
					buildWordCountMap(inputLine, wordCountMap);
				}

			}
			in.close();

			printWordCountValues(wordCountMap);
		} catch (IOException e) {
			e.getMessage();
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

	/**
	 * This method builds map having words as keys and value as its occurrence count
	 * 
	 * @param line
	 * @param wordCountMap
	 * @return 
	 */
	public static void buildWordCountMap(String line, Map<String, Integer> wordCountMap) {
		// Split the text and remove any trailing spaces
		for (String str : line.toLowerCase().replaceAll(REGEXP, " ").trim().split("\\s")) {
			if (str.trim().length() != 0)
				wordCountMap.put(str.trim(), wordCountMap.getOrDefault(str.trim(), 0) + 1);
		}
	}

}
