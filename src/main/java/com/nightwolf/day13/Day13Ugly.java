package com.nightwolf.day13;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Day13Ugly implements Day13 {
	@Override
	public Integer answerOne() {
		var lines = input().toList();
		var sum = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			List<Object> firstLineList = parseLine(lines.get(i));
			List<Object> secondLineList = parseLine(lines.get(i + 1));

			var compare = compare(firstLineList, secondLineList);
			if (compare < 0) {
				sum += i / 3 + 1;
			}
		}
		return sum;
	}

	@Override
	public Integer answerTwo() {
		List<List<Object>> lists = new ArrayList<>();
		var lines = input().toList();
		for (int i = 0; i < lines.size(); i += 3) {
			lists.add(parseLine(lines.get(i)));
			lists.add(parseLine(lines.get(i + 1)));
		}

		List<Object> firstDivider = List.of(List.of(2));
		List<Object> secondDivider = List.of(List.of(6));
		lists.add(firstDivider);
		lists.add(secondDivider);

		lists.sort(this::compare);
		var firstDividerIndex = lists.indexOf(firstDivider) + 1;
		var secondDividerIndex = lists.indexOf(secondDivider) + 1;

		return firstDividerIndex * secondDividerIndex;
	}

	private static List<Object> parseLine(String firstLine) {
		var chars = firstLine.toCharArray();
		Deque<List<Object>> listStack = new ArrayDeque<>();
		List<Object> list = new ArrayList<>();
		StringBuilder number = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			var c = chars[i];
			if (c == '[') {
				if (i != 0) {
					listStack.add(list);
					list = new ArrayList<>();
				}
				if (!listStack.isEmpty()) {
					listStack.getLast().add(list);
				}
			}
			if (c >= '0' && c <= '9') {
				number.append(c);
			}
			if ((c == ',' || c == ']') && number.length() > 0) {
				list.add(Integer.parseInt(number.toString()));
				number = new StringBuilder();
			}
			if (c == ']') {
				if (!listStack.isEmpty()) {
					list = listStack.pollLast();
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int compare(List<Object> firstList, List<Object> secondList) {
		for (int i = 0; i < Math.max(firstList.size(), secondList.size()); i++) {
			if (i >= firstList.size()) {
				return -1;
			}
			if (i >= secondList.size()) {
				return 1;
			}
			var first = firstList.get(i);
			var second = secondList.get(i);
			if (first instanceof List<?> f && second instanceof List<?> s) {
				var a = compare((List<Object>) f, (List<Object>) s);
				if (a != 0) {
					return a;
				}
			} else if (first instanceof List<?> f && second instanceof Integer s) {
				var a = compare((List<Object>) f, List.of(s));
				if (a != 0) {
					return a;
				}
			} else if (first instanceof Integer f && second instanceof List<?> s) {
				var a = compare(List.of(f), (List<Object>) s);
				if (a != 0) {
					return a;
				}
			}
			if (first instanceof Integer f && second instanceof Integer s) {
				var a = f.compareTo(s);
				if (a != 0) {
					return a;
				}
			}
		}

		return 0;
	}

	public static void main(String[] args) {
		var day = new Day13Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}
}
