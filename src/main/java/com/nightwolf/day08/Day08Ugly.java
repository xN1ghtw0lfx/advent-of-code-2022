package com.nightwolf.day08;
import java.util.List;

public class Day08Ugly implements Day08 {

	@Override
	public Long answerOne() {
		var strings = input().toList();
		int[][] trees = parseTrees(strings);
		long counter = 0;
		for (int i = 0; i < trees.length; i++) {
			inner:
			for (int j = 0; j < trees[i].length; j++) {
				var tree = trees[i][j];
				if (i == 0 || j == 0 || i == strings.size() - 1 || j == strings.get(0).length() - 1) {
					counter++;
					continue;
				}

				//Check top
				for (int k = i - 1; k >= 0; k--) {
					if (trees[k][j] >= tree) {
						break;
					}
					if (trees[k][j] < tree && k == 0) {
						counter++;
						continue inner;
					}
				}
				//Check bottom
				for (int k = i + 1; k < trees.length; k++) {
					if (trees[k][j] >= tree) {
						break;
					}
					if (trees[k][j] < tree && k == trees.length - 1) {
						counter++;
						continue inner;
					}
				}

				//Check right
				for (int k = j + 1; k < trees[i].length; k++) {
					if (trees[i][k] >= tree) {
						break;
					}
					if (trees[i][k] < tree && k == trees[i].length - 1) {
						counter++;
						continue inner;
					}
				}
				//Check left
				for (int k = j - 1; k >= 0; k--) {
					if (trees[i][k] >= tree) {
						break;
					}
					if (trees[i][k] < tree && k == 0) {
						counter++;
						continue inner;
					}
				}

			}
		}

		return counter;
	}

	@Override
	public Long answerTwo() {
		var strings = input().toList();
		int[][] trees = parseTrees(strings);
		long best = 0;
		for (int i = 0; i < trees.length; i++) {
			for (int j = 0; j < trees[i].length; j++) {
				var tree = trees[i][j];
				if (i == 0 || j == 0 || i == strings.size() - 1 || j == strings.get(0).length() - 1) {
					continue;
				}

				long top = 0;
				//Check top
				for (int k = i - 1; k >= 0; k--) {
					top++;
					if (trees[k][j] >= tree) {
						break;
					}
				}
				long bottom = 0;
				//Check bottom
				for (int k = i + 1; k < trees.length; k++) {
					bottom++;
					if (trees[k][j] >= tree) {
						break;
					}
				}

				long right = 0;
				//Check right
				for (int k = j + 1; k < trees[i].length; k++) {
					right++;
					if (trees[i][k] >= tree) {
						break;
					}
				}
				long left = 0;
				//Check left
				for (int k = j - 1; k >= 0; k--) {
					left++;
					if (trees[i][k] >= tree) {
						break;
					}
				}

				best = Math.max(best, top * bottom * right * left);
			}
		}

		return best;
	}

	private static int[][] parseTrees(List<String> strings) {
		int[][] trees = new int[strings.size()][strings.get(0).length()];
		for (int i = 0; i < strings.size(); i++) {
			var s = strings.get(i);
			var chars = s.toCharArray();
			for (int j = 0; j < chars.length; j++) {
				trees[i][j] = chars[j] - '0';
			}
		}
		return trees;
	}

	public static void main(String[] args) {
		var day01 = new Day08Ugly();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}

}