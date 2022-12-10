package com.nightwolf.day12;
import static com.nightwolf.day12.Day12Ugly.Point.p;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Day12Ugly implements Day12 {
	@Override
	public Integer answerOne() {
		var heightMap = parseInput();
		return calcBestPathInt(heightMap, heightMap.start());
	}

	@Override
	public Integer answerTwo() {
		HeightMap heightMap = parseInput();
		int currentBest = Integer.MAX_VALUE;

		var map = heightMap.map();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				if (map[i][j] != 0) {
					continue;
				}

				var steps = calcBestPathInt(heightMap, p(j, i));

				if (steps < currentBest) {
					currentBest = steps;
				}

			}
		}

		return currentBest;
	}

	private int calcBestPathInt(HeightMap heightMap, Point start) {
		return calcBestPathMap(heightMap, start).getOrDefault(heightMap.end(), Pair.of(null, Integer.MAX_VALUE)).getRight();
	}

	public HashMap<Point, Pair<Point, Integer>> calcBestPathMap(HeightMap heightMap, Point start) {
		var directions = List.of(p(-1, 0), p(1, 0), p(0, -1), p(0, 1));
		var pointsToCheck = List.of(start);

		var checkedPoints = new HashMap<Point, Pair<Point, Integer>>();
		checkedPoints.put(start, Pair.of(null, 0));

		while (true) {
			var newPointsToCheck = new ArrayList<Point>();
			for (Point point : pointsToCheck) {
				for (Point direction : directions) {
					var newPoint = point.add(direction);
					if (!checkedPoints.containsKey(newPoint) && pointInGrid(newPoint, heightMap.map()[0].length, heightMap.map().length)
							&& isReachable(heightMap.map(), point, newPoint)) {
						newPointsToCheck.add(newPoint);
						if (newPoint.equals(heightMap.end())) {
							checkedPoints.put(newPoint, Pair.of(point, checkedPoints.get(point).getRight() + 1));
							return checkedPoints;
						}
						checkedPoints.put(newPoint, Pair.of(point, checkedPoints.get(point).getRight() + 1));
					}
				}

			}
			pointsToCheck = newPointsToCheck;
			if (pointsToCheck.isEmpty()) {
				return checkedPoints;
			}
		}
	}

	private static boolean isReachable(int[][] map, Point point, Point newPoint) {
		return map[newPoint.y][newPoint.x] - map[point.y][point.x] <= 1;
	}

	private boolean pointInGrid(Point point, int width, int height) {
		return point.x >= 0 && point.x < width && point.y >= 0 && point.y < height;
	}

	public HeightMap parseInput() {
		var lines = input().toList();
		int[][] map = new int[lines.size()][];
		Point start = null, end = null;
		for (int i = 0; i < lines.size(); i++) {
			var line = lines.get(i);
			var chars = line.toCharArray();
			map[i] = new int[chars.length];
			for (int j = 0; j < chars.length; j++) {
				var aChar = chars[j];

				if (aChar == 'S') {
					start = p(j, i);
					map[i][j] = 0;
				} else if (aChar == 'E') {
					end = p(j, i);
					map[i][j] = 'z' - 'a';
				} else {
					map[i][j] = aChar - 'a';
				}
			}
		}
		return new HeightMap(start, end, map);
	}

	public static void main(String[] args) {
		var day = new Day12Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public record HeightMap(Point start, Point end, int[][] map) {
	}

	public record Point(int x, int y) {

		public static Point p(int x, int y) {
			return new Point(x, y);
		}

		public Point add(Point other) {
			return p(this.x + other.x, this.y + other.y);
		}
	}
}
