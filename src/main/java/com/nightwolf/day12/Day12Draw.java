package com.nightwolf.day12;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Day12Draw {

	public static void main(String[] args) {
		var day12 = new Day12Ugly();
		var heightMap = day12.parseInput();
		var pointPairHashMap = day12.calcBestPathMap(heightMap, heightMap.start());
		var frame = new Frame(heightMap.map()[0].length, heightMap.map().length);
		List<Day12Ugly.Point> points = new ArrayList<>();
		var parent = pointPairHashMap.get(heightMap.end()).getLeft();
		while (parent != null) {
			points.add(parent);
			parent = pointPairHashMap.get(parent).getLeft();
		}
		frame.surface.draw(heightMap.map(), points);
	}

	public static class Surface extends JPanel {

		private int[][] map;
		private List<Day12Ugly.Point> points;

		public Surface() {
			map = new int[0][];
			points = new ArrayList<>();
		}

		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.fillRect(0, 0, 5000, 5000);

			g2d.scale(Frame.SCALE, Frame.SCALE);
			g2d.translate(1, 1);
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					g2d.setColor(Color.getHSBColor(map[i][j] / (360f / 26f), 1, 1));
					g2d.drawRect(j * 2, i * 2, 1, 1);
				}
			}

			for (Day12Ugly.Point point : points) {
				g2d.setColor(Color.black);
				g2d.fillRect(point.x() * 2, point.y() * 2, 1, 1);
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}

		public void draw(int[][] map, List<Day12Ugly.Point> points) {
			this.map = map;
			this.points = points;
			repaint();
		}

	}

	public static class Frame extends JFrame {

		private final Surface surface;

		public static final int SCALE = 5;

		public Frame(int x, int y) {
			surface = new Surface();
			initUI(x, y);
		}

		private void initUI(int x, int y) {
			add(surface);

			setTitle("Snek");
			setSize(x * 2 * SCALE + 25, y * 2 * SCALE + 45);
			setLocationRelativeTo(null);
			getContentPane().setBackground(Color.black);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
			setVisible(true);

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		}
	}

}
