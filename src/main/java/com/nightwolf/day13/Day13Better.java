package com.nightwolf.day13;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Day13Better implements Day13 {
	@Override
	public Integer answerOne() {
		var lines = input().toList();
		var sum = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			var firstLine = parseLine(lines.get(i));
			var secondLine = parseLine(lines.get(i + 1));

			var analyze = compare(firstLine, secondLine);
			if (analyze == -1) {
				sum += i / 3 + 1;
			}
		}

		return sum;
	}

	@Override
	public Integer answerTwo() {
		var lists = new ArrayList<>();
		var lines = input().toList();

		for (int i = 0; i < lines.size(); i += 3) {
			lists.add(parseLine(lines.get(i)));
			lists.add(parseLine(lines.get(i + 1)));
		}

		List<Object> firstDivider = List.of(List.of(2));
		lists.add(firstDivider);
		List<Object> secondDivider = List.of(List.of(6));
		lists.add(secondDivider);

		lists.sort(this::compare);
		var firstDividerIndex = lists.indexOf(firstDivider) + 1;
		var secondDividerIndex = lists.indexOf(secondDivider) + 1;

		return firstDividerIndex * secondDividerIndex;
	}

	private List<Object> parseLine(String line) {
		var om = new ObjectMapper();
		try {
			return om.readValue(line, new TypeReference<>() {
			});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) {
		if (o1 instanceof Integer i1) {
			if (o2 instanceof Integer i2) {
				return i1.compareTo(i2);
			} else {
				return compare(List.of(i1), o2);
			}
		} else {
			if (o2 instanceof Integer i2) {
				return compare(o1, List.of(i2));
			}
			List<Object> l1 = (List<Object>) o1;
			List<Object> l2 = (List<Object>) o2;
			for (int i = 0; i < Math.min(l1.size(), l2.size()); i++) {
				var v = compare(l1.get(i), l2.get(i));
				if (v != 0) {
					return v;
				}
			}
			return Integer.compare(l1.size(), l2.size());
		}
	}

	public static void main(String[] args) {
		var day = new Day13Better();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}
}
