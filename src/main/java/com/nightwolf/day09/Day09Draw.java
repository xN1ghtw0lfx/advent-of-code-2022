package com.nightwolf.day09;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Day09Draw {

	public void visualize() {
		var rope = new Day09Ugly.Rope(720, 1);
		Frame ex = new Frame(rope);
		rope.setDrawCallback(ex.surface::draw);

		new Day09Ugly().input().forEach(rope::apply);
	}

	public static void main(String[] args) {
		var day01 = new Day09Draw();
		day01.visualize();
	}

	public static class Surface extends JPanel {

		public static final int TRANSLATE_X = 500;
		public static final int TRANSLATE_Y = 250;
		private final Day09Ugly.Rope rope;

		public Surface(Day09Ugly.Rope rope) {
			this.rope = rope;
		}

		private void doDrawing(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;
			g2d.fillRect(0, 0, 5000, 5000);

			g2d.scale(4, 4);
			if (rope == null) {
				return;
			}
			for (int i = rope.getTails().size() - 1; i >= 0; i--) {
				var tail = rope.getTails().get(i);
				g2d.setPaint(Color.getHSBColor(i / 360f, 1, 1));
				var tailPoint = (Point) tail.getPoint().clone();
				tailPoint.translate(TRANSLATE_X, TRANSLATE_Y);

				g2d.drawLine(tailPoint.x, tailPoint.y, tailPoint.x, tailPoint.y);
			}
			g2d.setPaint(Color.red);
			var headPoint = (Point) rope.getHead().getPoint().clone();
			headPoint.translate(TRANSLATE_X, TRANSLATE_Y);
			g2d.drawLine(headPoint.x, headPoint.y, headPoint.x, headPoint.y);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}

		int counter = 0;

		public void draw() {
			counter++;
			if (counter % 2 == 0) {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			repaint();
		}

	}

	public static class Frame extends JFrame {

		private final Surface surface;

		public Frame(Day09Ugly.Rope rope) {
			surface = new Surface(rope);
			initUI();
		}

		private void initUI() {
			add(surface);

			setTitle("Snek");
			setSize(800, 800);
			setLocationRelativeTo(null);
			getContentPane().setBackground(Color.black);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
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