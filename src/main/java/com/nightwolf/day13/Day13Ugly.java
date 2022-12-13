package com.nightwolf.day13;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Day13Ugly implements Day13 {
	@Override
	public Integer answerOne() {
		var lines = input().toList();
		var sum = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			var firstLine = lines.get(i);
			var secondLine = lines.get(i + 1);
			var chars = firstLine.toCharArray();
			Deque<List<Object>> listStack = new ArrayDeque<>();
			List<Object> firstLineList = null;
			for (int j = 0; j < chars.length; j++) {
				var c = chars[j];
				if (c == '[') {
					if (firstLineList != null) {
						listStack.add(firstLineList);
					}
					firstLineList = new ArrayList<>();
					if (!listStack.isEmpty()) {
						listStack.getLast().add(firstLineList);
					}
				} else if (c == '1' && chars[j + 1] == '0') {
					firstLineList.add(10);
					j++;
				} else if (c >= '0' && c <= '9') {
					firstLineList.add(chars[j] - '0');
				} else if (c == ']') {
					if (!listStack.isEmpty()) {
						firstLineList = listStack.pollLast();
					}
				}
			}
			chars = secondLine.toCharArray();
			listStack = new ArrayDeque<>();
			List<Object> secondLineList = null;
			for (int j = 0; j < chars.length; j++) {
				var c = chars[j];
				if (c == '[') {
					if (secondLineList != null) {
						listStack.add(secondLineList);
					}
					secondLineList = new ArrayList<>();
					if (!listStack.isEmpty()) {
						listStack.getLast().add(secondLineList);
					}
				} else if (c == '1' && chars[j + 1] == '0') {
					secondLineList.add(10);
					j++;
				} else if (c >= '0' && c <= '9') {
					secondLineList.add(chars[j] - '0');
				} else if (c == ']') {
					if (!listStack.isEmpty()) {
						secondLineList = listStack.pollLast();
					}
				}
			}

			System.out.println(firstLineList);
			System.out.println(secondLineList);

			var analyze = analyzeList(firstLineList, secondLineList);
			if (analyze == -1) {
				sum += i / 3 + 1;
			}
			System.out.println(analyze == -1 ? "ordered" : "not ordered");
			System.out.println();
		}
		return sum;
	}

	public int analyzeList(List<Object> firstList, List<Object> secondList) {
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
				var a = analyzeList((List<Object>) f, (List<Object>) s);
				if (a != 0) {
					return a;
				}
			} else if (first instanceof List<?> f && second instanceof Integer s) {
				var a = analyzeList((List<Object>) f, List.of(s));
				if (a != 0) {
					return a;
				}
			} else if (first instanceof Integer f && second instanceof List<?> s) {
				var a = analyzeList(List.of(f), (List<Object>) s);
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

	@Override
	public Integer answerTwo() {
		List<List<Object>> lists = new ArrayList<>();
		var lines = input().toList();
		var sum = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			var firstLine = lines.get(i);
			var secondLine = lines.get(i + 1);
			var chars = firstLine.toCharArray();
			Deque<List<Object>> listStack = new ArrayDeque<>();
			List<Object> firstLineList = null;
			for (int j = 0; j < chars.length; j++) {
				var c = chars[j];
				if (c == '[') {
					if (firstLineList != null) {
						listStack.add(firstLineList);
					}
					firstLineList = new ArrayList<>();
					if (!listStack.isEmpty()) {
						listStack.getLast().add(firstLineList);
					}
				} else if (c == '1' && chars[j + 1] == '0') {
					firstLineList.add(10);
					j++;
				} else if (c >= '0' && c <= '9') {
					firstLineList.add(chars[j] - '0');
				} else if (c == ']') {
					if (!listStack.isEmpty()) {
						firstLineList = listStack.pollLast();
					}
				}
			}
			chars = secondLine.toCharArray();
			listStack = new ArrayDeque<>();
			List<Object> secondLineList = null;
			for (int j = 0; j < chars.length; j++) {
				var c = chars[j];
				if (c == '[') {
					if (secondLineList != null) {
						listStack.add(secondLineList);
					}
					secondLineList = new ArrayList<>();
					if (!listStack.isEmpty()) {
						listStack.getLast().add(secondLineList);
					}
				} else if (c == '1' && chars[j + 1] == '0') {
					secondLineList.add(10);
					j++;
				} else if (c >= '0' && c <= '9') {
					secondLineList.add(chars[j] - '0');
				} else if (c == ']') {
					if (!listStack.isEmpty()) {
						secondLineList = listStack.pollLast();
					}
				}
			}
			lists.add(firstLineList);
			lists.add(secondLineList);
		}
		List<Object> firstDivider = List.of(List.of(2));
		List<Object> secondDivider = List.of(List.of(6));
		lists.add(firstDivider);
		lists.add(secondDivider);

		Collections.sort(lists, this::analyzeList);
		for (var list : lists) {
			System.out.println(list);
		}

		var firstDividerIndex = lists.indexOf(firstDivider) + 1;
		System.out.println(firstDividerIndex);
		var secondDividerIndex = lists.indexOf(secondDivider) + 1;
		System.out.println(secondDividerIndex);

		return firstDividerIndex * secondDividerIndex;
	}

	public static void main(String[] args) throws JsonProcessingException {
		var day = new Day13Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}
}
