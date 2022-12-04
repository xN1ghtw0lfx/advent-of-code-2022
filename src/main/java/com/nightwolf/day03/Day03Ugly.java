package com.nightwolf.day03;
import java.util.ArrayList;
import java.util.List;

public class Day03Ugly implements Day03 {
	@Override
	public Long answerOne() {
		List<String> compartment1 = new ArrayList<>();
		List<String> compartment2 = new ArrayList<>();
		input().forEach(l -> {
			compartment1.add(l.substring(0, l.length() - l.length() / 2));
			compartment2.add(l.substring(l.length() - l.length() / 2, l.length()));
		});
		List<Character> sameItems = new ArrayList<>();
		outer:
		for (int i = 0; i < compartment1.size(); i++) {
			var compartment1Items = compartment1.get(i);
			var compartment2Items = compartment2.get(i);
			for (int j = 0; j < compartment2Items.length(); j++) {
				var compartment2Item = compartment2Items.charAt(j);
				if (compartment1Items.contains(Character.toString(compartment2Item))) {
					sameItems.add(compartment2Item);
					continue outer;
				}
			}
		}

		return sameItems.stream().mapToLong(this::calcPrio).sum();
	}

	@Override
	public Long answerTwo() {
		List<List<String>> groups = getGroups();
		List<Character> sameItems = new ArrayList<>();
		outer:
		for (List<String> group : groups) {
			var firstElf = group.get(0);
			for (int i = 0; i < firstElf.length(); i++) {
				var charAt = firstElf.charAt(i);
				if (group.get(1).contains(Character.toString(charAt)) && group.get(2).contains(Character.toString(charAt))) {
					sameItems.add(charAt);
					continue outer;
				}
			}
		}

		return sameItems.stream().mapToLong(this::calcPrio).sum();
	}

	private List<List<String>> getGroups() {
		List<List<String>> groups = new ArrayList<>();
		int counter = 0;
		List<String> group = null;
		for (String string : input().toList()) {
			if (counter == 0) {
				group = new ArrayList<>();
			}
			group.add(string);
			counter++;
			if (counter == 3) {
				groups.add(group);
				counter = 0;
			}
		}
		return groups;
	}

	private long calcPrio(char character) {
		if (character >= 'a' && character <= 'z') {
			return character - 'a' + 1;
		}
		return character - 'A' + 27;
	}

	public static void main(String[] args) {
		var day = new Day03Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}
}
