package com.nightwolf.day04;
import java.util.List;

import com.nightwolf.DayTest;

class Day04Test extends DayTest<Day04, Long, Long> {

	protected static List<Day04> getDays() {
		return getDays(Day04.class);
	}

	@Override
	protected Long answerOne() {
		return 540L;
	}

	@Override
	protected Long answerTwo() {
		return 872L;
	}
}