package com.nightwolf.day12;
import static com.nightwolf.day12.Day12Nice.Position.p;
import static com.nightwolf.day12.Day12Nice.PositionDistance.pd;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;

public class Day12Nice implements Day12 {
	@Override
	public Integer answerOne() {
		char[][] grid = input().map(String::toCharArray).toArray(char[][]::new);
		var start = p(0, 0);
		var end = p(0, 0);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'S') {
					start = p(i, j);
					grid[i][j] = 'a';
				} else if (grid[i][j] == 'E') {
					end = p(i, j);
					grid[i][j] = 'z';
				}
			}
		}

		var q = new ArrayDeque<PositionDistance>();
		q.add(pd(0, start.r(), start.c()));

		var vis = new HashSet<Position>();
		vis.add(start);

		while (!q.isEmpty()) {
			var pd = q.pollFirst();
			for (Position ne : List.of(p(pd.r() + 1, pd.c()), p(pd.r() - 1, pd.c()), p(pd.r(), pd.c() + 1), p(pd.r(), pd.c() - 1))) {
				if (ne.r() < 0 || ne.c() < 0 || ne.r() >= grid.length || ne.c() >= grid[0].length)
					continue;
				if (vis.contains(ne))
					continue;
				if (grid[ne.r()][ne.c()] - grid[pd.r()][pd.c()] > 1)
					continue;

				if (ne.equals(end))
					return pd.d() + 1;

				vis.add(p(ne.r(), ne.c()));
				q.add(pd(pd.d() + 1, ne.r(), ne.c()));
			}
		}

		return -1;
	}

	@Override
	public Integer answerTwo() {
		char[][] grid = input().map(String::toCharArray).toArray(char[][]::new);
		var end = new Position(0, 0);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'S') {
					grid[i][j] = 'a';
				} else if (grid[i][j] == 'E') {
					end = p(i, j);
					grid[i][j] = 'z';
				}
			}
		}

		var q = new ArrayDeque<PositionDistance>();
		q.add(pd(0, end.r(), end.c()));

		var vis = new HashSet<Position>();
		vis.add(end);

		while (!q.isEmpty()) {
			var pd = q.pollFirst();
			for (Position ne : List.of(p(pd.r() + 1, pd.c()), p(pd.r() - 1, pd.c()), p(pd.r(), pd.c() + 1), p(pd.r(), pd.c() - 1))) {
				if (ne.r() < 0 || ne.c() < 0 || ne.r() >= grid.length || ne.c() >= grid[0].length)
					continue;
				if (vis.contains(ne))
					continue;
				if (grid[ne.r()][ne.c()] - grid[pd.r()][pd.c()] < -1)
					continue;

				if (grid[ne.r()][ne.c()] == 'a')
					return pd.d() + 1;

				vis.add(p(ne.r(), ne.c()));
				q.add(pd(pd.d() + 1, ne.r(), ne.c()));
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		var day = new Day12Nice();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public record Position(int r, int c) {
		public static Position p(int r, int c) {
			return new Position(r, c);
		}
	}

	public record PositionDistance(int d, int r, int c) {
		public static PositionDistance pd(int d, int r, int c) {
			return new PositionDistance(d, r, c);
		}
	}
}
