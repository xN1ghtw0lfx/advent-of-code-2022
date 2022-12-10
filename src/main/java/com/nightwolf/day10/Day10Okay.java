package com.nightwolf.day10;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day10Okay implements Day10 {
	@Override
	public Integer answerOne() {
		List<Integer> registerHistory = parseInput();
		return Stream.of(20, 60, 100, 140, 180, 220).mapToInt(i -> registerHistory.get(i) * i).sum();
	}

	@Override
	public String answerTwo() {
		List<Integer> registerHistory = parseInput();
		var sb = new StringBuilder();

		for (int i = 0; i < registerHistory.size(); i += 40) {
			for (int j = 0; j < 40; j++) {
				if (Math.abs(registerHistory.get(i + j) - j) <= 1) {
					sb.append("#");
				} else {
					sb.append(".");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private List<Integer> parseInput() {
		AtomicInteger x = new AtomicInteger(1);
		return input().map(l -> {
					if (l.equals("noop")) {
						return List.of(x.get());
					}
					var v = Integer.parseInt(l.split(" ")[1]);
					var before = x.getAndAdd(v);
					return List.of(before, before);
				})
				.flatMap(Collection::stream)
				.toList();
	}

	public static void main(String[] args) {
		var day = new Day10Okay();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2:\n" + day.answerTwo());
	}

}
