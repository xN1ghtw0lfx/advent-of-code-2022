package com.nightwolf;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface Day<T, U> {

	T answerOne();

	U answerTwo();

	default Stream<String> input() {
		return input0();
	}

	default Stream<String> input(int skip) {
		return input0(skip);
	}

	default Stream<String> input(int skip, int limit) {
		return input0(skip, limit);
	}

	private static Stream<String> input0() {
		return input(getCaller(), 0, -1);
	}

	private static Stream<String> input0(int skip) {
		return input(getCaller(), skip, -1);
	}

	private static Stream<String> input0(int skip, int limit) {
		return input(getCaller(), skip, limit);
	}

	private static Stream<String> input(Class<?> type, int skip, int limit) {
		try {
			var resource = type.getResource("input.txt");
			assert resource != null;

			var bufferedReader = Files.newBufferedReader(Path.of(resource.toURI()));
			var stream = bufferedReader.lines().onClose(() -> {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}).skip(skip);

			if (limit >= 0) {
				return stream.limit(limit);
			}

			return stream;
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[4].getClassName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
