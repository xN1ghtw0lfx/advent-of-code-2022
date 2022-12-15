package com.nightwolf.day15;

import java.util.List;

import com.nightwolf.DayTest;

class Day15Test extends DayTest<Day15, String, String> {

	protected static List<Day15> getDays() {
		return getDays(Day15.class);
	}

	@Override
	protected String answerOne() {
		return "5181556";
	}

	@Override
	protected String answerTwo() {
		return "12817603219131";
	}
}