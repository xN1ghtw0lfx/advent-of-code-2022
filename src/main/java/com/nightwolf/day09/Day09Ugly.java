package com.nightwolf.day09;
import static com.nightwolf.day09.Day09Ugly.Direction.Down;
import static com.nightwolf.day09.Day09Ugly.Direction.Left;
import static com.nightwolf.day09.Day09Ugly.Direction.Right;
import static com.nightwolf.day09.Day09Ugly.Direction.Up;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class Day09Ugly implements Day09 {

	@Override
	public Long answerOne() {
		var rope = new Rope(1, 1);
		input().forEach(rope::apply);
		return rope.getTail(0).getAmountOfUniquePositions();
	}

	@Override
	public Long answerTwo() {
		var rope = new Rope(9, 1);
		input().forEach(rope::apply);
		return rope.getTail(8).getAmountOfUniquePositions();
	}

	public static void main(String[] args) {
		var day01 = new Day09Ugly();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}

	public static class Rope {

		private final List<Knot> knots;
		private final int multiplicand;

		private Runnable drawCallback;

		public Rope(int tails, int multiplicand) {
			this.multiplicand = multiplicand;
			this.knots = new ArrayList<>(tails + 1);
			for (int i = 0; i < tails + 1; i++) {
				this.knots.add(new Knot());
			}
		}

		public void setDrawCallback(Runnable drawCallback) {
			this.drawCallback = drawCallback;
		}

		public Knot getTail(int i) {
			return knots.get(i + 1);
		}

		public Knot getHead() {
			return knots.get(0);
		}

		public List<Knot> getTails() {
			return knots.subList(1, knots.size());
		}

		public void apply(String line) {
			var split = line.split(" ");
			var direction = Direction.direction(split[0]);
			var amount = Integer.parseInt(split[1]) * multiplicand;
			for (int i = 0; i < amount; i++) {
				for (int j = 0; j < knots.size(); j++) {
					var knot = knots.get(j);

					if (j == 0) {
						knot.translate(direction);
						continue;
					}

					var previousKnot = knots.get(j - 1);
					if (!previousKnot.adjacent(knot)) {
						var y = previousKnot.getPoint().y - knot.getPoint().y;
						var x = previousKnot.getPoint().x - knot.getPoint().x;

						var xTrans = 0;
						var yTrans = 0;
						if (y < 0) {
							yTrans = Up.getMove().y;
						} else if (y > 0) {
							yTrans = Down.getMove().y;
						}
						if (x > 0) {
							xTrans = Right.getMove().x;
						} else if (x < 0) {
							xTrans = Left.getMove().x;
						}
						knot.translate(xTrans, yTrans);
					}
				}
				if (drawCallback != null) {
					drawCallback.run();
				}
			}
		}
	}

	public enum Direction {
		Up("U", new Point(0, -1)),
		Down("D", new Point(0, 1)),
		Right("R", new Point(1, 0)),
		Left("L", new Point(-1, 0));

		final String input;

		final Point move;

		Direction(String input, Point move) {
			this.input = input;
			this.move = move;
		}

		public Point getMove() {
			return this.move;
		}

		public static Direction direction(String input) {
			return Arrays.stream(values()).filter(d -> d.input.equals(input)).findFirst().orElseThrow();
		}
	}

	public static class Knot {

		private final Point point;

		private final Set<Point> history;

		public Knot() {
			this.point = new Point(0, 0);
			this.history = new HashSet<>();
		}

		public Point getPoint() {
			return point;
		}

		public void translate(Direction direction) {
			var p = direction.getMove();
			translate(p.x, p.y);
		}

		public void translate(int x, int y) {
			history.add((Point) point.clone());
			point.translate(x, y);
		}

		public long getAmountOfUniquePositions() {
			return Stream.concat(history.stream(), Stream.of(point)).distinct().count();
		}

		public boolean adjacent(Knot knot) {
			return (int) point.distance(knot.getPoint()) <= 1;
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Knot.class.getSimpleName() + "[", "]")
					.add("point=" + point)
					.toString();
		}
	}

}