package com.nightwolf.day15;

import static com.nightwolf.day15.Day15Ugly.Position.p;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class Day15Ugly implements Day15 {

	//Sensor at x=3988693, y=3986119: closest beacon is at x=3979063, y=3856315
	@Override
	public String answerOne() {
		var lines = input().toList();
		List<Sensor> sensors = new ArrayList<>();
		var minX = 0;
		var maxX = 0;
		for (var line : lines) {
			var sensor = parseSensor(line);
			sensors.add(sensor);

			minX = Math.min(minX, sensor.position().x());
			maxX = Math.max(maxX, sensor.position().x());

			minX = Math.min(minX, sensor.position().x() - sensor.distance());
			maxX = Math.max(maxX, sensor.position().x() + sensor.distance());

			minX = Math.min(minX, sensor.nearestBeacon().x());
			maxX = Math.max(maxX, sensor.nearestBeacon().x());

		}

		List<Position> occupiedPositions = new ArrayList<>();
		for (var i = minX; i <= maxX; i++) {
			var position = p(i, 2000000);
			for (var sensor : sensors) {
				var distance = sensor.position().distance(position);
				if (sensor.distance() >= distance) {
					occupiedPositions.add(position);
					break;
				}
			}
		}

		for (var sensor : sensors) {
			occupiedPositions.remove(sensor.nearestBeacon());
		}

		return Integer.toString(occupiedPositions.size());
	}

	@Override
	public String answerTwo() {
		var sensors = input().map(Day15Ugly::parseSensor).toList();

		var possibleSensors = new ArrayList<Sensor>();
		for (var outer : sensors) {
			for (var inner : sensors) {
				if (inner == outer) {
					continue;
				}

				var distance = outer.position().distance(inner.position());
				if (distance == outer.distance() + inner.distance() + 2) {
					possibleSensors.add(outer);
				}
			}
		}

		var sets = possibleSensors.parallelStream().map(s -> s.getEdgePositions(s.distance() + 1)).flatMap(Collection::stream).toList();

		outer:
		for (var position : sets) {
			if (position.x() < 0 || position.x() > 4_000_000 || position.y() < 0 || position.y() > 4_000_000) {
				continue;
			}

			for (var sensor : sensors) {
				var distance = sensor.position().distance(position);
				if (sensor.distance() >= distance) {
					continue outer;
				}
			}
			return Long.toString(position.x() * 4000000L + position.y());
		}

		return "-1";
	}

	@NotNull
	private static Sensor parseSensor(String line) {
		var replace = line.replace("Sensor at x=", "").replace(", y=", " ").replace(": closest beacon is at x=", " ").replace(", y=", " ");
		var split = replace.split(" ");

		var sensorP = p(split[0], split[1]);
		var beaconP = p(split[2], split[3]);
		var distance = sensorP.distance(beaconP);
		return new Sensor(sensorP, beaconP, distance);
	}

	public static void main(String[] args) {
		var day = new Day15Ugly();

		System.out.println("Answer 1: " + day.answerOne());
		System.out.println("Answer 2: " + day.answerTwo());
	}

	public record Sensor(Position position, Position nearestBeacon, int distance) {

		public List<Position> getEdgePositions(int distance) {
			var maxX = position().x() + distance;
			var minX = position().x() - distance;
			var y = 0;

			List<Position> edgePositions = new ArrayList<>(3000000);
			for (var i = minX; i <= maxX; i++) {

				edgePositions.add(p(i, position.y() + y));
				if (y != 0) {
					edgePositions.add(p(i, position.y() - y));
				}

				if (i < position.x()) {
					y++;
				} else {
					y--;
				}
			}

			return edgePositions;
		}
	}

	public record Position(int x, int y) {

		public static Position p(int x, int y) {
			return new Position(x, y);
		}

		public static Position p(String x, String y) {
			return p(Integer.parseInt(x), Integer.parseInt(y));
		}

		public int distance(Position other) {
			var x = Math.abs(this.x - other.x);
			var y = Math.abs(this.y - other.y);
			return x + y;
		}
	}

}
