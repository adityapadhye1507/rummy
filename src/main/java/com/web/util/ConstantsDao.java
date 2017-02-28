package com.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Constant class to store predefined sequences of numbers
 * */

@Repository("constantsDao")
public class ConstantsDao {
	public static ArrayList<List<Integer>> fullSequence;
	public static ArrayList<List<Integer>> fourSequence;
	public static ArrayList<List<Integer>> threeSequence;
	
	public ConstantsDao() {
		fillFullSequence();
		fillFourSequence();
		fillThreeSequence();
	}

	private void fillThreeSequence() {
		threeSequence = new ArrayList<List<Integer>>();

		Integer[] s1 = new Integer[] { 1, 2, 3 };
		Integer[] s2 = new Integer[] { 2, 3, 4 };
		Integer[] s3 = new Integer[] { 3, 4, 5 };
		Integer[] s4 = new Integer[] { 4, 5, 6 };
		Integer[] s5 = new Integer[] { 5, 6, 7 };
		Integer[] s6 = new Integer[] { 6, 7, 8 };
		Integer[] s7 = new Integer[] { 7, 8, 9 };
		Integer[] s8 = new Integer[] { 8, 9, 10 };
		Integer[] s9 = new Integer[] { 9, 10, 11 };
		Integer[] s10 = new Integer[] { 10, 11, 12 };
		Integer[] s11 = new Integer[] { 11, 12, 13};
		Integer[] s12 = new Integer[] { 1, 12, 13};

		threeSequence.add(Arrays.asList(s1));
		threeSequence.add(Arrays.asList(s2));
		threeSequence.add(Arrays.asList(s3));
		threeSequence.add(Arrays.asList(s4));
		threeSequence.add(Arrays.asList(s5));
		threeSequence.add(Arrays.asList(s6));
		threeSequence.add(Arrays.asList(s7));
		threeSequence.add(Arrays.asList(s8));
		threeSequence.add(Arrays.asList(s9));
		threeSequence.add(Arrays.asList(s10));
		threeSequence.add(Arrays.asList(s11));
		threeSequence.add(Arrays.asList(s12));
	}

	private void fillFourSequence() {
		fourSequence = new ArrayList<List<Integer>>();

		Integer[] s1 = new Integer[] { 1, 2, 3, 4 };
		Integer[] s2 = new Integer[] { 2, 3, 4, 5 };
		Integer[] s3 = new Integer[] { 3, 4, 5, 6 };
		Integer[] s4 = new Integer[] { 4, 5, 6, 7 };
		Integer[] s5 = new Integer[] { 5, 6, 7, 8 };
		Integer[] s6 = new Integer[] { 6, 7, 8, 9 };
		Integer[] s7 = new Integer[] { 7, 8, 9, 10 };
		Integer[] s8 = new Integer[] { 8, 9, 10, 11};
		Integer[] s9 = new Integer[] { 9, 10, 11, 12};
		Integer[] s10 = new Integer[] { 10, 11, 12, 13};
		Integer[] s11 = new Integer[] { 1, 11, 12, 13};


		fourSequence.add(Arrays.asList(s1));
		fourSequence.add(Arrays.asList(s2));
		fourSequence.add(Arrays.asList(s3));
		fourSequence.add(Arrays.asList(s4));
		fourSequence.add(Arrays.asList(s5));
		fourSequence.add(Arrays.asList(s6));
		fourSequence.add(Arrays.asList(s7));
		fourSequence.add(Arrays.asList(s8));
		fourSequence.add(Arrays.asList(s9));
		fourSequence.add(Arrays.asList(s10));
		fourSequence.add(Arrays.asList(s11));

	}

	private void fillFullSequence() {
		fullSequence = new ArrayList<List<Integer>>();

		Integer[] s1 = new Integer[] { 1, 2, 3, 4, 5, 6, 7 };
		Integer[] s2 = new Integer[] { 2, 3, 4, 5, 6, 7, 8 };
		Integer[] s3 = new Integer[] { 3, 4, 5, 6, 7, 8, 9 };
		Integer[] s4 = new Integer[] { 4, 5, 6, 7, 8, 9, 10 };
		Integer[] s5 = new Integer[] { 5, 6, 7, 8, 9, 10, 11 };
		Integer[] s6 = new Integer[] { 6, 7, 8, 9, 10, 11, 12 };
		Integer[] s7 = new Integer[] { 7, 8, 9, 10, 11, 12, 13 };
		Integer[] s8 = new Integer[] { 1, 8, 9, 10, 11, 12, 13 };

		fullSequence.add(Arrays.asList(s1));
		fullSequence.add(Arrays.asList(s2));
		fullSequence.add(Arrays.asList(s3));
		fullSequence.add(Arrays.asList(s4));
		fullSequence.add(Arrays.asList(s5));
		fullSequence.add(Arrays.asList(s6));
		fullSequence.add(Arrays.asList(s7));
		fullSequence.add(Arrays.asList(s8));
	}
}
