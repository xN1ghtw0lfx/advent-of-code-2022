package com.nightwolf.day04;
import static com.nightwolf.day04.Day04AI.RangeContainmentChecker.isContained;
import static com.nightwolf.day04.Day04AI.RangeOverlapChecker.isOverlapping;

import org.apache.commons.lang3.tuple.Pair;

public class Day04AI implements Day04 {
	@Override
	public Long answerOne() {
		return input()
				.map(l -> l.split(","))
				.map(s -> Pair.of(s[0].split("-"), s[1].split("-")))
				.map(p -> Pair.of(range1(p.getLeft()[0], p.getLeft()[1]), range1(p.getRight()[0], p.getRight()[1])))
				.filter(p -> isContained(p.getLeft(), p.getRight()))
				.count();
	}

	@Override
	public Long answerTwo() {
		return input()
				.map(l -> l.split(","))
				.map(s -> Pair.of(s[0].split("-"), s[1].split("-")))
				.map(p -> Pair.of(range2(p.getLeft()[0], p.getLeft()[1]), range2(p.getRight()[0], p.getRight()[1])))
				.filter(e -> isOverlapping(e.getLeft(), e.getRight()))
				.count();
	}

	public static void main(String[] args) {
		var day = new Day04AI();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	private static RangeContainmentChecker.Range range1(String start, String end) {
		return new RangeContainmentChecker.Range(Integer.parseInt(start), Integer.parseInt(end));
	}

	private static RangeOverlapChecker.Range range2(String start, String end) {
		return new RangeOverlapChecker.Range(Integer.parseInt(start), Integer.parseInt(end));
	}

	public static class RangeContainmentChecker {
		// Represents a range of numbers with a start and end point
		public static class Range {
			int start;
			int end;

			public Range(int start, int end) {
				this.start = start;
				this.end = end;
			}
		}

		public static boolean isContained(Range range1, Range range2) {
			// Check if range1 is fully contained in range2, or vice versa, or if the ranges are equal
			// by checking if the start and end of range1 are both greater than or equal to the start
			// of range2 and less than or equal to the end of range2, and vice versa
			return (range1.start >= range2.start && range1.end <= range2.end) ||
					(range2.start >= range1.start && range2.end <= range1.end) ||
					(range1.start == range2.start && range1.end == range2.end);
		}
	}

	public class RangeOverlapChecker {
		// Represents a range of numbers with a start and end point
		private static class Range {
			int start;
			int end;

			public Range(int start, int end) {
				this.start = start;
				this.end = end;
			}
		}

		public static boolean isOverlapping(Range range1, Range range2) {
			// Check if the ranges are overlapping by checking if the start of one range is
			// less than or equal to the end of the other range, and vice versa
			return range1.start <= range2.end && range2.start <= range1.end;
		}
	}

}
