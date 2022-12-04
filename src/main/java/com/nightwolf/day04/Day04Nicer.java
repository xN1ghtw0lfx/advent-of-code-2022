package com.nightwolf.day04;
import static com.nightwolf.day04.Day04Nicer.Range.range;

import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

public class Day04Nicer implements Day04 {

	@Override
	public Long answerOne() {
		return extractRanges()
				.filter(p -> p.getLeft().contains(p.getRight()) || p.getRight().contains(p.getLeft()))
				.count();
	}

	@Override
	public Long answerTwo() {
		return extractRanges()
				.filter(p -> p.getLeft().overlapsWith(p.getRight()))
				.count();
	}

	private Stream<Pair<Range, Range>> extractRanges() {
		return input()
				.map(l -> l.split(","))
				.map(s -> Pair.of(s[0].split("-"), s[1].split("-")))
				.map(p -> Pair.of(range(p.getLeft()[0], p.getLeft()[1]), range(p.getRight()[0], p.getRight()[1])));
	}

	public static void main(String[] args) {
		var day = new Day04Nicer();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public static class Range {
		int start;
		int end;

		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public static Range range(String start, String end) {
			return new Range(Integer.parseInt(start), Integer.parseInt(end));
		}

		public boolean overlapsWith(Range other) {
			return this.start <= other.end && other.start <= this.end;
		}

		public boolean contains(Range other) {
			return this.start >= other.start && this.end <= other.end;
		}

	}

}
