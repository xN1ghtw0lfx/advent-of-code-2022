package com.nightwolf.day07;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07Ugly implements Day07 {

	private final Directory root;

	public Day07Ugly() {
		root = parseDirectories();
		//		root.print(0);
	}

	@Override
	public Long answerOne() {
		return root.getDirectoriesWithSizeOfAtMost(100_000).stream()
				.mapToLong(Directory::getSize)
				.sum();
	}

	@Override
	public Long answerTwo() {
		var sizeToFreeUp = root.getSize() - 40_000_000;
		return root.getAllDirectoriesWithMinSize(sizeToFreeUp).stream()
				.min(Comparator.comparing(Directory::getSize))
				.orElseThrow()
				.getSize();
	}

	private Directory parseDirectories() {
		var root = new Directory("/", null);
		Directory currentDir = root;

		var lines = input(2).toList();
		for (String line : lines) {
			var split = line.split(" ");
			if (line.startsWith("$")) {
				if (split[1].equals("cd")) {
					if (split[2].equals("..")) {
						currentDir = currentDir.getParent();
					} else {
						currentDir = currentDir.getDirectory(split[2]);
					}
				}
			} else {
				if (split[0].equals("dir")) {
					currentDir.addChild(new Directory(split[1], currentDir));
				} else {
					currentDir.addChild(new File(split[1], currentDir, Long.parseLong(split[0])));
				}
			}
		}
		return root;
	}

	public static void main(String[] args) {
		var day01 = new Day07Ugly();

		System.out.println("Answer 1: " + day01.answerOne());
		System.out.println("Answer 2: " + day01.answerTwo());
	}

	public static abstract class SystemObject {

		protected final Directory parent;

		protected final String name;

		public SystemObject(String name, Directory parent) {
			this.name = name;
			this.parent = parent;
		}

		public abstract long getSize();

		public Directory getParent() {
			return parent;
		}

		public String getName() {
			return name;
		}

		public abstract void print(int indent);

		protected String getIdent(int indent) {
			return "\t".repeat(indent);
		}
	}

	public static class Directory extends SystemObject {

		protected final List<SystemObject> children;

		public Directory(String name, Directory parent) {
			super(name, parent);
			this.children = new ArrayList<>();
		}

		@Override
		public long getSize() {
			return children.stream().mapToLong(SystemObject::getSize).sum();
		}

		private Stream<Directory> directories() {
			return children.stream()
					.filter(c -> c instanceof Directory)
					.map(so -> (Directory) so);
		}

		public List<Directory> getDirectoriesWithSizeOfAtMost(long size) {
			var directories = directories()
					.filter(d -> d.getSize() <= size)
					.collect(Collectors.toList());

			var directories1 = directories()
					.map(d -> d.getDirectoriesWithSizeOfAtMost(size))
					.flatMap(Collection::stream)
					.toList();

			directories.addAll(directories1);
			return directories;
		}

		public List<Directory> getAllDirectoriesWithMinSize(long minSize) {
			var directories = new ArrayList<Directory>();
			directories.add(this);
			directories.addAll(directories()
					.filter(d -> d.getSize() >= minSize)
					.map(d -> d.getAllDirectoriesWithMinSize(minSize))
					.flatMap(Collection::stream).toList());

			return directories;
		}

		public void addChild(SystemObject child) {
			children.add(child);
		}

		public Directory getDirectory(String name) {
			return directories()
					.filter(c -> c.getName().equals(name))
					.findFirst()
					.orElseThrow();
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Directory.class.getSimpleName() + "[", "]")
					.add("name='" + getName() + "'")
					.add("size='" + getSize() + "'")
					.toString();
		}

		@Override
		public void print(int indent) {
			var indentStr = getIdent(indent);
			System.out.println(indentStr + "Directory " + name + " (" + getSize() + ")");
			children.forEach(c -> c.print(indent + 1));
		}
	}

	public static class File extends SystemObject {

		long size;

		public File(String name, Directory parent, long size) {
			super(name, parent);
			this.size = size;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public void print(int indent) {
			var indentStr = getIdent(indent);
			System.out.println(indentStr + "File " + name + " (" + getSize() + ")");
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", File.class.getSimpleName() + "[", "]")
					.add("name='" + name + "'")
					.add("size=" + size)
					.toString();
		}
	}

}