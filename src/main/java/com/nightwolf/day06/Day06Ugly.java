package com.nightwolf.day06;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Day06Ugly implements Day06 {

	private final int[] chars;

	public Day06Ugly() {
		chars = input().flatMapToInt(String::chars).toArray();
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
		var queue = new CircularFifoQueue<Character>(distinctLength);
		var result = 0;

		for (int i = 0; i < chars.length; i++) {
			queue.offer((char) chars[i]);
			if (queue.stream().distinct().count() == distinctLength) {
				result = i + 1;
				break;
			}
		}

		return result;
	}

	public static void main(String[] args) {
		var day01 = new Day06Ugly();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}
}