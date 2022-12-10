package com.nightwolf.day10;
import java.util.List;

public class Day10Ugly implements Day10 {
	@Override
	public Integer answerOne() {
		var cycles = List.of(20, 60, 100, 140, 180, 220);
		var sum = 0;
		var lines = input().toList();
		int x = 1;
		int cycle = 0;
		for (String line : lines) {
			var split = line.split(" ");
			if (split[0].equals("noop")) {
				cycle++;
				if (cycles.contains(cycle)) {
					sum += cycle * x;
				}
			} else {
				var valueToAdd = Integer.parseInt(split[1]);
				cycle++;
				if (cycles.contains(cycle)) {
					sum += cycle * x;
				}

				cycle++;
				if (cycles.contains(cycle)) {
					sum += cycle * x;
				}
				x += valueToAdd;
			}
		}
		System.out.println(x);
		return sum;
	}

	@Override
	public String answerTwo() {
		var lines = input().toList();
		int x = 1;
		int cycle = 0;
		var sb = new StringBuilder();
		for (String line : lines) {
			var split = line.split(" ");
			if (split[0].equals("noop")) {

				if (List.of(x - 1, x, x + 1).contains(cycle % 41)) {
					sb.append('#');
				} else {
					sb.append('.');
				}
				cycle++;
				if (cycle == 40) {
					sb.append("\n");
					cycle = 0;
				}
			} else {
				var valueToAdd = Integer.parseInt(split[1]);

				if (List.of(x - 1, x, x + 1).contains(cycle % 41)) {
					sb.append('#');
				} else {
					sb.append('.');
				}
				cycle++;
				if (cycle == 40) {
					sb.append("\n");
					cycle = 0;
				}

				if (List.of(x - 1, x, x + 1).contains(cycle % 41)) {
					sb.append('#');
				} else {
					sb.append('.');
				}
				cycle++;
				if (cycle == 40) {
					sb.append("\n");
					cycle = 0;
				}
				x += valueToAdd;
			}
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		var day = new Day10Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2:\n" + day.answerTwo());
	}

}
