package com.nightwolf.day14;

import static com.nightwolf.day14.Day14Ugly2.Position.p;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

public class Day14Ugly2 implements Day14 {

	@Override
	public String answerOne() {
		List<List<Position>> rocks = parseLines();
		var abyss = rocks.stream().flatMap(Collection::stream).mapToInt(Position::y).max().orElseThrow() + 1;
		var occupiedPoints = buildCave(rocks, null);
		var counter = simulateSand(occupiedPoints, abyss);

		return Integer.toString(counter);
	}

	@Override
	public String answerTwo() {
		List<List<Position>> rocks = parseLines();
		var floor = rocks.stream().flatMap(Collection::stream).mapToInt(Position::y).max().orElseThrow() + 2;
		var positionSet = buildCave(rocks, floor);
		var counter = simulateSand(positionSet, floor + 1);

		return Integer.toString(counter);
	}

	@NotNull
	private List<List<Position>> parseLines() {
		return input().map(l -> l.split(" -> "))
				.map(psa -> Arrays.stream(psa).map(ps -> {
					var split = ps.split(",");
					return p(split[0], split[1]);
				}).collect(Collectors.toList()))
				.toList();
	}

	@NotNull
	private static Set<Position> buildCave(List<List<Position>> rocks, Integer floor) {
		Set<Position> occupiedPositions = new PositionSet(floor);
		for (List<Position> rock : rocks) {
			for (int i = 0; i < rock.size() - 1; i++) {
				var from = rock.get(i);
				var to = rock.get(i + 1);

				var subtract = to.subtract(from);

				if (subtract.x() > 0) {
					for (int j = from.x(); j <= from.x() + subtract.x(); j++) {
						occupiedPositions.add(p(j, from.y()));
					}
				} else if (subtract.x() < 0) {
					for (int j = from.x(); j != from.x() + subtract.x() - 1; j--) {
						occupiedPositions.add(p(j, from.y()));
					}
				} else if (subtract.y() > 0) {
					for (int j = from.y(); j <= from.y() + subtract.y(); j++) {
						occupiedPositions.add(p(from.x(), j));
					}
				} else if (subtract.y() < 0) {
					for (int j = from.y(); j != from.y() + subtract.y() - 1; j--) {
						occupiedPositions.add(p(from.x(), j));
					}
				}
			}
		}

		return occupiedPositions;
	}

	private static int simulateSand(Set<Position> positions, int abyss) {
		var directions = List.of(p(0, 1), p(-1, 1), p(1, 1));
		var counter = 0;
		while (true) {
			var sandPosition = p(500, 0);
			if (positions.contains(sandPosition)) {
				return counter;
			}
			while (true) {
				Position newSandPosition = null;
				for (Position direction : directions) {
					var position = sandPosition.add(direction);

					if (sandPosition.y() == abyss) {
						return counter;
					}

					if (!positions.contains(position)) {
						newSandPosition = position;
						break;
					}
				}
				if (newSandPosition == null) {
					positions.add(sandPosition);
					counter++;
					break;
				}
				sandPosition = newSandPosition;
			}
		}
	}

	public static void main(String[] args) {
		var day = new Day14Ugly2();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public record Position(int x, int y) {

		public static Position p(int x, int y) {
			return new Position(x, y);
		}

		public static Position p(String x, String y) {
			return new Position(Integer.parseInt(x), Integer.parseInt(y));
		}

		public Position add(Position other) {
			return p(this.x + other.x, this.y + other.y);
		}

		public Position subtract(Position other) {
			return p(this.x - other.x, this.y - other.y);
		}
	}

	public static class PositionSet extends HashSet<Position> {

		private final Integer floor;

		public PositionSet(Integer floor) {
			this.floor = floor;
		}

		@Override
		public boolean contains(Object o) {
			return super.contains(o) || (floor != null && ((Position) o).y() == floor);
		}
	}
}
