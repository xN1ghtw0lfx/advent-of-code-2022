package com.nightwolf;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public abstract class DayTest<E extends Day<F, G>, F, G> {

	public static final Reflections reflections = new Reflections(new ConfigurationBuilder()
			.filterInputsBy(new FilterBuilder().includePackage("com.nightwolf"))
			.setUrls(ClasspathHelper.forPackage("com.nightwolf"))
			.setScanners(new SubTypesScanner()));

	protected static <T> List<T> getDays() {
		throw new UnsupportedOperationException();
	}

	protected static <T> List<T> getDays(Class<T> type) {
		return reflections.getSubTypesOf(type).stream()
				.map(c -> {
					try {
						return (T) c.getConstructor().newInstance();
					} catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				})
				.toList();
	}

	protected abstract F answerOne();

	@ParameterizedTest
	@MethodSource("getDays")
	void answerOne(E day) {
		Assertions.assertThat(day.answerOne()).isEqualTo(answerOne());
	}

	protected abstract G answerTwo();

	@ParameterizedTest
	@MethodSource("getDays")
	void answerTwo(E day) {
		Assertions.assertThat(day.answerTwo()).isEqualTo(answerTwo());
	}

}
