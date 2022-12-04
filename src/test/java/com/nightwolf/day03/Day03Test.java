package com.nightwolf.day03;
import java.util.List;

import com.nightwolf.DayTest;

class Day03Test extends DayTest<Day03, Long, Long> {

	protected static List<Day03> getDays() {
		return getDays(Day03.class);
	}

	@Override
	protected Long answerOne() {
		return 8105L;
	}

	@Override
	protected Long answerTwo() {
		return 2363L;
	}
}