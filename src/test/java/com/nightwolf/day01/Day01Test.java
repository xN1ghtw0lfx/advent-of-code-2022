package com.nightwolf.day01;

import java.util.List;

import com.nightwolf.DayTest;

class Day01Test extends DayTest<Day01, Long, Long> {

	protected static List<Day01> getDays() {
		return getDays(Day01.class);
	}

	@Override
	protected Long answerOne() {
		return 70613L;
	}

	@Override
	protected Long answerTwo() {
		return 205805L;
	}
}