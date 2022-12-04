package com.nightwolf.day06;
import java.util.List;

import com.nightwolf.DayTest;

class Day06Test extends DayTest<Day06, Long, Long> {

	protected static List<Day06> getDays() {
		return getDays(Day06.class);
	}

	@Override
	protected Long answerOne() {
		return 1531L;
	}

	@Override
	protected Long answerTwo() {
		return 2518L;
	}
}