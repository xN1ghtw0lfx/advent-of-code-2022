package com.nightwolf.day05;
import static com.nightwolf.day05.Day05Impl.Instruction.instruction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day05Impl implements Day05 {
	@Override
	public String answerOne() {
		Plan plan = getPlan(Crane9000::new);
		return plan.executePlan();
	}

	@Override
	public String answerTwo() {
		Plan plan = getPlan(Crane9001::new);
		return plan.executePlan();
	}

	private Plan getPlan(Function<Stacks, Crane> craneFunction) {
		var stacks = new Stacks();
		input(0, 8).forEach(l -> {
			for (int i = 0; i < 9; i++) {
				var charAt = l.charAt(i * 4 + 1);
				if (charAt == ' ') {
					continue;
				}
				stacks.add(i, charAt);
			}
		});

		var instructions = input(10)
				.map(l -> l.split(" "))
				.map(s -> instruction(s[1], s[3], s[5]))
				.toList();

		var crane = craneFunction.apply(stacks);

		return new Plan(crane, stacks, instructions);
	}

	public static void main(String[] args) {
		var day = new Day05Impl();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public static class Plan {
		private final Crane crane;
		private final Stacks stacks;
		private final List<Instruction> instructions;

		public Plan(Crane crane, Stacks stacks, List<Instruction> instructions) {
			this.crane = crane;
			this.stacks = stacks;
			this.instructions = instructions;
		}

		public String executePlan() {
			for (Instruction instruction : instructions) {
				crane.execute(instruction);
			}
			return stacks.getTopItems();
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Plan.class.getSimpleName() + "[", "]")
					.add("crane=" + crane)
					.add("stacks=" + stacks)
					.add("instructions=" + instructions)
					.toString();
		}
	}

	public static abstract class Crane {

		protected final Stacks stacks;

		protected Crane(Stacks stacks) {
			this.stacks = stacks;
		}

		abstract void execute(Instruction instruction);
	}

	public static class Crane9000 extends Crane {
		public Crane9000(Stacks stacks) {
			super(stacks);
		}

		@Override
		void execute(Instruction instruction) {
			var fromStack = stacks.get(instruction.from - 1);
			var toStack = stacks.get(instruction.to - 1);
			for (int i = 0; i < instruction.amount; i++) {
				var first = fromStack.pop();
				toStack.addFirst(first);
			}
		}

		@Override
		public String toString() {
			return "Crane9000";
		}
	}

	public static class Crane9001 extends Crane {
		public Crane9001(Stacks stacks) {
			super(stacks);
		}

		@Override
		void execute(Instruction instruction) {
			var fromStack = stacks.get(instruction.from - 1);
			var toStack = stacks.get(instruction.to - 1);
			var crates = new ArrayDeque<String>(instruction.amount);
			for (int i = 0; i < instruction.amount; i++) {
				crates.addFirst(fromStack.pop());
			}
			for (int i = 0; i < instruction.amount; i++) {
				toStack.addFirst(crates.pop());
			}
		}

		@Override
		public String toString() {
			return "Crane9001";
		}
	}

	public static class Stacks {
		private final List<Deque<String>> stacks;

		public Stacks() {
			this.stacks = new ArrayList<>();
			for (int i = 0; i < 9; i++) {
				stacks.add(new ArrayDeque<>());
			}
		}

		public void add(int stack, Character character) {
			stacks.get(stack).addLast(Character.toString(character));
		}

		public Deque<String> get(int stack) {
			return stacks.get(stack);
		}

		public String getTopItems() {
			return stacks.stream().map(Deque::getFirst).map(Objects::toString).collect(Collectors.joining());
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Stacks.class.getSimpleName() + "[", "]")
					.add("stacks=" + stacks)
					.toString();
		}
	}

	public static class Instruction {
		int amount;
		int from;
		int to;

		public Instruction(int amount, int from, int to) {
			this.from = from;
			this.to = to;
			this.amount = amount;
		}

		public static Instruction instruction(String amount, String from, String to) {
			return new Instruction(Integer.parseInt(amount), Integer.parseInt(from), Integer.parseInt(to));
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Instruction.class.getSimpleName() + "[", "]")
					.add("amount=" + amount)
					.add("from=" + from)
					.add("to=" + to)
					.toString();
		}
	}
}
