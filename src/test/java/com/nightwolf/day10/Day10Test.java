package com.nightwolf.day10;
import java.util.List;

import com.nightwolf.DayTest;

class Day10Test extends DayTest<Day10, Integer, String> {

	protected static List<Day10> getDays() {
		return getDays(Day10.class);
	}

	@Override
	protected Integer answerOne() {
		return 17940;
	}

	@Override
	protected String answerTwo() {
		return """
				########    ####    ######      ####        ####  ########      ####  ######## \s
				      ##  ##    ##  ##    ##  ##    ##        ##  ##              ##        ## \s
				    ##    ##        ######    ##    ##        ##  ######          ##      ##   \s
				  ##      ##        ##    ##  ########        ##  ##              ##    ##     \s
				##        ##    ##  ##    ##  ##    ##  ##    ##  ##        ##    ##  ##       \s
				########    ####    ######    ##    ##    ####    ##          ####    ######## \s
				""";
	}
}