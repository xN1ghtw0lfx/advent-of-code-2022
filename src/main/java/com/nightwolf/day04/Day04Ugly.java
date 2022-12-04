package com.nightwolf.day04;
import java.util.concurrent.atomic.AtomicInteger;

public class Day04Ugly implements Day04 {
	@Override
	public Long answerOne() {
		var adder = new AtomicInteger();
		input().forEach(l -> {
			var split = l.split(",");
			var leftSplit = split[0].split("-");
			var rightSplit = split[1].split("-");

			var leftStart = Long.parseLong(leftSplit[0]);
			var leftEnd = Long.parseLong(leftSplit[1]);

			var rightStart = Long.parseLong(rightSplit[0]);
			var rightEnd = Long.parseLong(rightSplit[1]);

			if (leftStart <= rightStart && leftEnd >= rightEnd) {
				adder.incrementAndGet();
			} else if (rightStart <= leftStart && rightEnd >= leftEnd) {
				adder.incrementAndGet();
			}
		});
		return adder.longValue();
	}

	@Override
	public Long answerTwo() {
		var adder = new AtomicInteger();
		input().forEach(l -> {
			var split = l.split(",");
			var leftSplit = split[0].split("-");
			var rightSplit = split[1].split("-");

			var leftStart = Long.parseLong(leftSplit[0]);
			var leftEnd = Long.parseLong(leftSplit[1]);

			var rightStart = Long.parseLong(rightSplit[0]);
			var rightEnd = Long.parseLong(rightSplit[1]);

			if (leftStart <= rightEnd && rightStart <= leftEnd) {
				adder.incrementAndGet();
			}
		});
		return adder.longValue();
	}

	public static void main(String[] args) {
		var day = new Day04Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}
}
