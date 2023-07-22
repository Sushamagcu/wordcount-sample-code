package com.sample.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.file.wordcount.WordCountMultiThreaded;

public class WordCountMultiThreadedTest {

	Thread[] threads = new Thread[2];

	@Test
	void setIndexRangeForThreadTest() {
		List<List<String>> sublists = new ArrayList<>();
		List<String> lineList = new ArrayList<>();
		lineList = Arrays.asList("<program>  Copyright (C) <year>  <name of author>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>");
		WordCountMultiThreaded.setIndexRangeForThread(lineList, sublists);
		assertEquals(1, sublists.size());

	}

	@Test
	void setIndexRangeForThreadCountTest() {
		List<List<String>> sublists = new ArrayList<>();
		List<String> lineList = new ArrayList<>();
		lineList = Arrays.asList("<program>  Copyright (C) <year>  <name of author>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>");
		WordCountMultiThreaded.setIndexRangeForThread(lineList, sublists);
		assertEquals(5, sublists.size());

	}

	@Test
	void setIndexRangeForThreadCount2Test() {
		List<List<String>> sublists = new ArrayList<>();
		List<String> lineList = new ArrayList<>();
		lineList = Arrays.asList("<program>  Copyright (C) <year>  <name of author>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"<program>  Copyright (C) <year>  <name of author>");
		WordCountMultiThreaded.setIndexRangeForThread(lineList, sublists);
		assertEquals(5, sublists.size());

	}

	@Test
	void processMultiThreadWordCountTest() {
		List<List<String>> sublists = new ArrayList<>();
		List<String> lineList = new ArrayList<>();
		Map<String, Integer> wordCountMap = new HashMap<>();
		int THREAD_COUNT = 5;
		lineList = Arrays.asList("<program>  Copyright (C) <year>  <name of author>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>",
				"<program>  Copyright (C) <year>  <name of author>");
		try {
			WordCountMultiThreaded.setIndexRangeForThread(lineList, sublists);
			WordCountMultiThreaded.processMultiThreadWordCount(sublists, new Thread[THREAD_COUNT], wordCountMap);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(wordCountMap);
		assertEquals(5, sublists.size());

	}
	
}
