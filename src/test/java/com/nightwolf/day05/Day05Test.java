package com.nightwolf.day05;
import java.util.List;

import com.nightwolf.DayTest;

class Day05Test extends DayTest<Day05, String, String> {

	protected static List<Day05> getDays() {
		return getDays(Day05.class);
	}

	@Override
	protected String answerOne() {
		return "BWNCQRMDB";
	}

	@Override
	protected String answerTwo() {
		return "NHWZCBNBF";
	}
}