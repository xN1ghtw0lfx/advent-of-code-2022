package com.nightwolf.day13;
import java.util.List;

import com.nightwolf.DayTest;

class Day13Test extends DayTest<Day13, Integer, Integer> {

	protected static List<Day13> getDays() {
		return getDays(Day13.class);
	}

	@Override
	protected Integer answerOne() {
		return 5330;
	}

	@Override
	protected Integer answerTwo() {
		return 27648;
	}
}