package com.nightwolf.day09;
import java.util.List;

import com.nightwolf.DayTest;

class Day09Test extends DayTest<Day09, Long, Long> {

	protected static List<Day09> getDays() {
		return getDays(Day09.class);
	}

	@Override
	protected Long answerOne() {
		return 5930L;
	}

	@Override
	protected Long answerTwo() {
		return 2443L;
	}
}