package com.nightwolf.day14;

import static com.nightwolf.day14.Day14Ugly.Point.p;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class Day14Ugly implements Day14 {

	@Override
	public String answerOne() {
		List<List<Point>> rocks = parseLines();
		char[][] cave = buildCave(rocks, 300, -400, false);
		var counter = simulateSand(cave, -400);

		printCave(cave);

		return Integer.toString(counter);
	}

	@Override
	public String answerTwo() {
		List<List<Point>> rocks = parseLines();
		char[][] cave = buildCave(rocks, 1000, 0, true);
		int counter = simulateSand(cave, 0);

		printCave(cave);

		return Integer.toString(counter);
	}

	@NotNull
	private List<List<Point>> parseLines() {
		List<List<Point>> rocks = new ArrayList<>();
		for (String line : input().toList()) {
			var pointsString = line.split(" -> ");
			List<Point> points = new ArrayList<>();
			for (String pointString : pointsString) {
				var split = pointString.split(",");
				var p = p(split[0], split[1]);
				points.add(p);
			}
			rocks.add(points);
		}
		return rocks;
	}

	@NotNull
	private static char[][] buildCave(List<List<Point>> rocks, int width, int translateX, boolean addFloor) {
		char[][] cave = new char[200][width];
		for (List<Point> rock : rocks) {
			for (int i = 0; i < rock.size() - 1; i++) {
				var from = rock.get(i);
				var to = rock.get(i + 1);

				var subtract = to.subtract(from);

				if (subtract.x() > 0) {
					for (int j = from.x(); j <= from.x() + subtract.x(); j++) {
						cave[from.y()][j + translateX] = '#';
					}
				} else if (subtract.x() < 0) {
					for (int j = from.x(); j != from.x() + subtract.x() - 1; j--) {
						cave[from.y()][j + translateX] = '#';
					}
				} else if (subtract.y() > 0) {
					for (int j = from.y(); j <= from.y() + subtract.y(); j++) {
						cave[j][from.x() + translateX] = '#';
					}
				} else if (subtract.y() < 0) {
					for (int j = from.y(); j != from.y() + subtract.y() - 1; j--) {
						cave[j][from.x() + translateX] = '#';
					}
				}
			}
		}

		if (addFloor) {
			var maxY = rocks.stream().flatMap(Collection::stream).mapToInt(Point::y).max().orElseThrow();
			for (int i = 0; i < cave[maxY].length; i++) {
				cave[maxY + 2][i] = '#';
			}
		}

		return cave;
	}

	private static int simulateSand(char[][] cave, int translateX) {
		var directions = List.of(p(0, 1), p(-1, 1), p(1, 1));
		var counter = 0;
		outer:
		while (true) {
			var sand = p(500, 0);
			if (cave[sand.y()][sand.x() + translateX] != 0) {
				break;
			}
			while (true) {
				var newSandPosition = sand;
				for (Point direction : directions) {
					var position = sand.add(direction);

					if (position.y() >= cave.length || position.x() + translateX >= cave[0].length) {
						break outer;
					}

					if (cave[position.y()][position.x() + translateX] == 0) {
						newSandPosition = position;
						break;
					}
				}
				if (sand.equals(newSandPosition)) {
					cave[sand.y()][sand.x() + translateX] = 'o';
					counter++;
					break;
				}
				sand = newSandPosition;
			}
		}
		return counter;
	}

	private static void printCave(char[][] cave) {
		for (char[] chars : cave) {
			for (char aChar : chars) {
				if (aChar == 0) {
					System.out.print(' ');
				} else {
					System.out.print(aChar);
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		var day = new Day14Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public record Point(int x, int y) {

		public static Point p(int x, int y) {
			return new Point(x, y);
		}

		public static Point p(String x, String y) {
			return new Point(Integer.parseInt(x), Integer.parseInt(y));
		}

		public Point add(Point other) {
			return p(this.x + other.x, this.y + other.y);
		}

		public Point subtract(Point other) {
			return p(this.x - other.x, this.y - other.y);
		}
	}
}