package com.nightwolf.day11;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day11Impl implements Day11 {
	@Override
	public Long answerOne() {
		var monkeys = parseInput(3);

		for (long i = 0; i < 20; i++) {
			for (Monkey monkey : monkeys) {
				monkey.doYourThing(monkeys);
			}
		}

		return getMonkeyBusiness(monkeys);
	}

	@Override
	public Long answerTwo() {
		var monkeys = parseInput(1);

		for (long i = 0; i < 10_000; i++) {
			for (Monkey monkey : monkeys) {
				monkey.doYourThing(monkeys);
			}
		}

		return getMonkeyBusiness(monkeys);
	}

	private List<Monkey> parseInput(int reliefCoefficient) {
		List<Monkey> monkeys = new ArrayList<>();
		var lines = input().toList();

		Deque<Long> items = null;
		Operator operator = null;
		long number = 0;
		long divisor = 0;
		int ifTrue = 0;
		int ifFalse;
		for (int i = 0; i < lines.size(); i++) {
			var j = i % 7;
			if (j == 0 || j == 6) {
				continue;
			}

			var line = lines.get(i);
			if (j == 1) {
				var substring = line.substring(18);
				var split = substring.split(", ");
				items = Arrays.stream(split).map(Long::parseLong).collect(Collectors.toCollection(ArrayDeque::new));
			} else if (j == 2) {
				if (line.contains("old * old")) {
					operator = Operator.Square;
					continue;
				} else if (line.contains("old *")) {
					operator = Operator.Product;
				} else {
					operator = Operator.Sum;
				}
				number = Long.parseLong(line.split(" ")[7]);
			} else if (j == 3) {
				var split = line.split(" ");
				divisor = Long.parseLong(split[5]);
			} else if (j == 4) {
				var split = line.split(" ");
				ifTrue = Integer.parseInt(split[9]);
			} else {
				var split = line.split(" ");
				ifFalse = Integer.parseInt(split[9]);
				monkeys.add(new Monkey(Long.toString(i / 7), items, operator, number, divisor, ifTrue, ifFalse, reliefCoefficient));
			}
		}
		return monkeys;
	}

	private static long getMonkeyBusiness(List<Monkey> monkeys) {
		return monkeys.stream()
				.sorted(Comparator.comparing(Monkey::getInspects).reversed())
				.limit(2)
				.mapToLong(Monkey::getInspects)
				.reduce(Math::multiplyExact).orElseThrow();
	}

	public static void main(String[] args) {
		var day = new Day11Impl();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public static class Monkey {

		private final String name;
		private final Deque<Long> items;
		private final Operator operator;
		private final long number;
		private final long divisor;

		private final int ifTrue;
		private final int ifFalse;

		private final int reliefCoefficient;
		private long inspects;

		public Monkey(String name, Deque<Long> items, Operator operator, long number, long divisor, int ifTrue, int ifFalse, int reliefCoefficient) {
			this.name = name;
			this.items = items;
			this.operator = operator;
			this.number = number;
			this.divisor = divisor;
			this.ifTrue = ifTrue;
			this.ifFalse = ifFalse;
			this.reliefCoefficient = reliefCoefficient;
		}

		public void doYourThing(List<Monkey> monkeys) {
			for (int i = items.size(); i > 0; i--) {
				var worryLevel = items.pollFirst();
				assert worryLevel != null;
				var afterInspect = operator.apply(worryLevel, number);
				var afterBored = afterInspect / reliefCoefficient;
				if (reliefCoefficient == 1) {
					afterBored = afterBored % getLcm(monkeys);
				}
				if (afterBored % divisor == 0) {
					handOver(monkeys.get(ifTrue), afterBored);
				} else {
					handOver(monkeys.get(ifFalse), afterBored);
				}
				inspects++;
			}
		}

		private long getLcm(List<Monkey> monkeys) {
			return monkeys.stream().mapToLong(Monkey::getDivisor).reduce(Math::multiplyExact).orElseThrow();
		}

		public void handOver(Monkey other, long item) {
			other.addItem(item);
		}

		public void addItem(long item) {
			items.addLast(item);
		}

		public long getInspects() {
			return inspects;
		}

		public long getDivisor() {
			return divisor;
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Monkey.class.getSimpleName() + "[", "]")
					.add("name=" + name)
					.add("items=" + items)
					.add("operator=" + operator)
					.add("number=" + number)
					.add("divisor=" + divisor)
					.add("ifTrue=" + ifTrue)
					.add("ifFalse=" + ifFalse)
					.add("inspects=" + inspects)
					.toString();
		}
	}

	public enum Operator {
		Sum(Long::sum), Square((wl, n) -> (long) Math.pow(wl, 2)), Product(Math::multiplyExact);

		private final BiFunction<Long, Long, Long> function;

		Operator(BiFunction<Long, Long, Long> function) {
			this.function = function;
		}

		public long apply(long worryLevel, long n) {
			return function.apply(worryLevel, n);
		}

		public String toString(long n) {
			return switch (this) {
				case Sum -> "increases by %d".formatted(n);
				case Square -> "is multiplied by itself";
				case Product -> "is multiplied by %d".formatted(n);
			};
		}
	}
}
