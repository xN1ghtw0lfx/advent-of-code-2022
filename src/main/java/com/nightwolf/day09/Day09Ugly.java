package com.nightwolf.day09;
import static com.nightwolf.day09.Day09Ugly.Direction.Down;
import static com.nightwolf.day09.Day09Ugly.Direction.Left;
import static com.nightwolf.day09.Day09Ugly.Direction.LeftDown;
import static com.nightwolf.day09.Day09Ugly.Direction.LeftUp;
import static com.nightwolf.day09.Day09Ugly.Direction.Right;
import static com.nightwolf.day09.Day09Ugly.Direction.RightDown;
import static com.nightwolf.day09.Day09Ugly.Direction.RightUp;
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
		var rope = new Rope(1, false, 1);
		input().forEach(rope::apply);
		return rope.getTail(0).getAmountOfUniquePositions();
	}

	@Override
	public Long answerTwo() {
		var rope = new Rope(9, false, 1);
		input().forEach(rope::apply);
		return rope.getTail(8).getAmountOfUniquePositions();
	}

	public static void main(String[] args) {
		var day01 = new Day09Ugly();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}

	public static class Rope {

		private final Knot head;
		private final List<Knot> tails;
		private final boolean debug;
		private final int multiplicand;

		private Runnable drawCallback;

		public Rope(int tails, boolean debug, int multiplicand) {
			this.debug = debug;
			this.multiplicand = multiplicand;
			this.head = new Knot();
			this.tails = new ArrayList<>();
			for (int i = 0; i < tails; i++) {
				this.tails.add(new Knot());
			}
		}

		public void setDrawCallback(Runnable drawCallback) {
			this.drawCallback = drawCallback;
		}

		public Knot getTail(int i) {
			return tails.get(i);
		}

		public Knot getHead() {
			return head;
		}

		public List<Knot> getTails() {
			return tails;
		}

		public void apply(String line) {
			var split = line.split(" ");
			var direction = Direction.direction(split[0]);
			var amount = Integer.parseInt(split[1]) * multiplicand;
			for (int i = 0; i < amount; i++) {
				head.translate(direction);
				for (int j = 0; j < tails.size(); j++) {
					var knot = tails.get(j);

					Knot previousKnot = head;
					if (j != 0) {
						previousKnot = tails.get(j - 1);
					}

					if (!previousKnot.adjacent(knot)) {
						var y = previousKnot.point.y - knot.point.y;
						var x = previousKnot.point.x - knot.point.x;

						if (x > 0) {
							if (y == 0) {
								knot.translate(Right);
							} else if (y > 0) {
								knot.translate(RightDown);
							} else {
								knot.translate(RightUp);
							}
						} else if (x < 0) {
							if (y > 0) {
								knot.translate(LeftDown);
							} else if (y == 0) {
								knot.translate(Left);
							} else {
								knot.translate(LeftUp);
							}
						} else {
							if (y < 0) {
								knot.translate(Up);
							} else if (y > 0) {
								knot.translate(Down);
							}
						}
					}
				}
				if (debug) {
					print();
				}
				if (drawCallback != null) {
					drawCallback.run();
				}
			}
		}

		private void print() {
			for (int i = -25; i < 25; i++) {
				inner:
				for (int j = -25; j < 25; j++) {
					if (head.point.equals(new Point(j, i))) {
						System.out.print("H");
						continue;
					}
					for (int k = 0; k < tails.size(); k++) {
						if (tails.get(k).point.equals(new Point(j, i))) {
							System.out.print("" + (k + 1));
							continue inner;
						}
					}
					if (new Point(0, 0).equals(new Point(j, i))) {
						System.out.print("S");
						continue;
					}
					System.out.print(".");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
	}

	public enum Direction {
		Up("U", new Point(0, -1)),
		Down("D", new Point(0, 1)),
		Right("R", new Point(1, 0)),
		Left("L", new Point(-1, 0)),
		RightUp("RU", new Point(1, -1)),
		RightDown("RD", new Point(1, 1)),
		LeftUp("LU", new Point(-1, -1)),
		LeftDown("LD", new Point(-1, 1));

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
			history.add((Point) point.clone());
			var p = direction.getMove();
			point.translate(p.x, p.y);
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