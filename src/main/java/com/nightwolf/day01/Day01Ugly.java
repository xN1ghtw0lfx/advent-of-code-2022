package com.nightwolf.day01;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Day01Ugly implements Day01 {

	private final LongAdder longAdder;
	private final List<Long> calories;

	public Day01Ugly() {
		longAdder = new LongAdder();
		calories = new ArrayList<>();
		input().forEach(l -> {
			if (l.isBlank()) {
				calories.add(longAdder.longValue());
				longAdder.reset();
				return;
			}

			longAdder.add(Long.parseLong(l));
		});
	}

	@Override
	public Long answerOne() {
		return calories.stream().mapToLong(Long::longValue).max().orElseThrow();
	}

	@Override
	public Long answerTwo() {
		var longs = calories.stream().mapToLong(Long::longValue).sorted().toArray();
		return longs[longs.length - 1] + longs[longs.length - 2] + longs[longs.length - 3];
	}

	public static void main(String[] args) {
		var day01 = new Day01Ugly();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}
}