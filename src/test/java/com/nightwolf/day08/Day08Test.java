package com.nightwolf.day08;
import java.util.List;

import com.nightwolf.DayTest;

class Day08Test extends DayTest<Day08, Long, Long> {

	protected static List<Day08> getDays() {
		return getDays(Day08.class);
	}

	@Override
	protected Long answerOne() {
		return 1711L;
	}

	@Override
	protected Long answerTwo() {
		return 301392L;
	}
}