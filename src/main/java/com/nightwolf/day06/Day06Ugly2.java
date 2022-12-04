package com.nightwolf.day06;
import static com.nightwolf.WTF.wtf;

import java.util.List;
import java.util.Set;

public class Day06Ugly2 implements Day06 {

	private final List<Character> chars;

	public Day06Ugly2() {
		chars = input().flatMapToInt(String::chars).mapToObj(i -> (char) i).toList();
	}

	@Override
	public Long answerOne() {
		return getMarker(4);
	}

	@Override
	public Long answerTwo() {
		return getMarker(14);
	}

	private long getMarker(int distinctLength) {
		for (int i = distinctLength; i < chars.size(); i++) {
			if (Set.copyOf(chars.subList(i - distinctLength, i)).size() == distinctLength) {
				return i;
			}
		}
		throw wtf();
	}

	public static void main(String[] args) {
		var day01 = new Day06Ugly2();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}
}