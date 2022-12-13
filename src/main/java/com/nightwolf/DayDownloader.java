package com.nightwolf;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

public class DayDownloader {

	public static void main(String[] args) throws IOException, InterruptedException {
		var day = AoCUtils.getDay();

		var basePath = Path.of(System.getProperty("user.dir"));

		var javaBasePath = basePath.resolve("src/main/java/com/nightwolf");
		var javaDayDirPath = javaBasePath.resolve("day%02d".formatted(day));
		if (!Files.exists(javaDayDirPath)) {
			Files.createDirectory(javaDayDirPath);
		}

		var interfacePath = javaDayDirPath.resolve("Day%02d.java".formatted(day));
		if (!Files.exists(interfacePath)) {
			var input = Objects.requireNonNull(DayDownloader.class.getResourceAsStream("DayInterface.java.txt"));
			var interfaceTemplate = IOUtils.toString(input);
			interfaceTemplate = interfaceTemplate.replace("{{day}}", "%02d".formatted(day));
			Files.writeString(interfacePath, interfaceTemplate, StandardOpenOption.CREATE_NEW);
		}

		var implName = "Ugly";
		var impPath = javaDayDirPath.resolve("Day%02d%s.java".formatted(day, implName));
		if (!Files.exists(impPath)) {
			var input = Objects.requireNonNull(DayDownloader.class.getResourceAsStream("DayImpl.java.txt"));
			var implTemplate = IOUtils.toString(input);
			implTemplate = implTemplate.replace("{{day}}", "%02d".formatted(day));
			implTemplate = implTemplate.replace("{{name}}", "%s".formatted(implName));
			Files.writeString(impPath, implTemplate, StandardOpenOption.CREATE_NEW);
		}

		var testBasePath = basePath.resolve("src/test/java/com/nightwolf");
		var testDayDirPath = testBasePath.resolve("day%02d".formatted(day));
		if (!Files.exists(testDayDirPath)) {
			Files.createDirectory(testDayDirPath);
		}

		var testPath = testDayDirPath.resolve("Day%02dTest.java".formatted(day));
		if (!Files.exists(testPath)) {
			var input = Objects.requireNonNull(DayDownloader.class.getResourceAsStream("DayTest.java.txt"));
			var testTemplate = IOUtils.toString(input);
			testTemplate = testTemplate.replace("{{day}}", "%02d".formatted(day));
			Files.writeString(testPath, testTemplate, StandardOpenOption.CREATE_NEW);
		}

		var resourceBasePath = basePath.resolve("src/main/resources/com/nightwolf");
		var resourceDayPath = resourceBasePath.resolve("day%02d".formatted(day));
		if (!Files.exists(resourceDayPath)) {
			Files.createDirectory(resourceDayPath);
		}

		var client = AoCUtils.getClient();

		var challengeInputPath = resourceDayPath.resolve("input.txt");
		if (!Files.exists(challengeInputPath)) {
			var challengeInputRequest = getChallengeInputRequest(day);
			var challengeInput = client.send(challengeInputRequest, HttpResponse.BodyHandlers.ofString());
			Files.writeString(challengeInputPath, challengeInput.body());
		}

		System.out.println("Challenge can be found at: https://adventofcode.com/2022/day/" + day);
	}

	private static HttpRequest getChallengeInputRequest(int day) {
		return HttpRequest.newBuilder()
				.GET()
				.uri(URI.create("https://adventofcode.com/2022/day/" + day + "/input"))
				.build();
	}

}
