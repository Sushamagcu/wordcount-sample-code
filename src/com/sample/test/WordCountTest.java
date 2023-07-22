package com.sample.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.file.wordcount.WordCount;

public class WordCountTest {

	@Test
	void populateWordCountTest() {
		String line = "<program>  Copyright (C) <year>  <name of author>";
		Map<String, Integer> wordCountMap = new HashMap<>();
		WordCount.buildWordCountMap(line, wordCountMap);
		assertEquals(7, wordCountMap.size());
		assertEquals(1, wordCountMap.get("year"));

	}

	@Test
	void populateWordCountMultiLineTest() {
		String multiLineString = "Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>\n"
				+ " Everyone IS permitted to copy and distribute verbatim copies\n"
				+ "of this license COPYRIGHT document, but changing it is not allowed.";

		Map<String, Integer> wordCountMap = new HashMap<>();
		WordCount.buildWordCountMap(multiLineString, wordCountMap);
		System.out.println(wordCountMap);
		assertEquals(2, wordCountMap.get("is"));

	}
}
