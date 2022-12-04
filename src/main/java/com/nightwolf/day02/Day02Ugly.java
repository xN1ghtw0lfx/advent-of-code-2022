package com.nightwolf.day02;
import java.util.ArrayList;
import java.util.List;

public class Day02Ugly implements Day02 {
	@Override
	public Long answerOne() {
		List<Long> scores = new ArrayList<>();
		input()
				.forEach(l -> {
					var score = 0L;
					var oponent = l.charAt(0);
					var me = l.charAt(2);
					switch (oponent) {
						case 'A' -> {
							if (me == 'X') {
								score += 4;
							} else if (me == 'Y') {
								score += 8;
							} else if (me == 'Z') {
								score += 3;
							}
						}
						case 'B' -> {
							if (me == 'X') {
								score += 1;
							} else if (me == 'Y') {
								score += 5;
							} else if (me == 'Z') {
								score += 9;
							}
						}
						case 'C' -> {
							if (me == 'X') {
								score += 7;
							} else if (me == 'Y') {
								score += 2;
							} else if (me == 'Z') {
								score += 6;
							}
						}
					}
					scores.add(score);
				});

		return scores.stream().mapToLong(Long::longValue).sum();
	}

	@Override
	public Long answerTwo() {
		List<Long> scores2 = new ArrayList<>();
		input()
				.forEach(l -> {
					var score = 0L;
					var oponent = l.charAt(0);
					var me = l.charAt(2);
					switch (oponent) {
						case 'A' -> { //Rock
							if (me == 'X') { //Scissor 3
								score += 3;
							} else if (me == 'Y') { //Rock 1 + 3
								score += 4;
							} else if (me == 'Z') { //Paper 2 + 6
								score += 8;
							}
						}
						case 'B' -> { //Paper
							if (me == 'X') { //Rock 1
								score += 1;
							} else if (me == 'Y') { //Paper 2 + 3
								score += 5;
							} else if (me == 'Z') { //Scissor 3 + 6
								score += 9;
							}
						}
						case 'C' -> { //Scissor
							if (me == 'X') { //Paper 2
								score += 2;
							} else if (me == 'Y') { //Scissor 3 + 3
								score += 6;
							} else if (me == 'Z') { //Rock 1 + 6
								score += 7;
							}
						}
					}
					scores2.add(score);
				});

		return scores2.stream().mapToLong(Long::longValue).sum();
	}

	public static void main(String[] args) {
		var day02 = new Day02Ugly();

		System.out.println("Answer 1: " + day02.answerOne());
		System.out.println("Answer 2: " + day02.answerTwo());
	}
}