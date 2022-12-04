package com.nightwolf.day02;
import java.util.List;

import com.nightwolf.DayTest;

class Day02Test extends DayTest<Day02, Long, Long> {

	protected static List<Day02> getDays() {
		return getDays(Day02.class);
	}

	@Override
	protected Long answerOne() {
		return 14163L;
	}

	@Override
	protected Long answerTwo() {
		return 12091L;
	}
}