package com.nightwolf.day14;

import java.util.List;

import com.nightwolf.DayTest;

class Day14Test extends DayTest<Day14, String, String> {

	protected static List<Day14> getDays() {
		return getDays(Day14.class);
	}

	@Override
	protected String answerOne() {
		return "665";
	}

	@Override
	protected String answerTwo() {
		return "25434";
	}
}