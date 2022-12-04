package com.nightwolf;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class DayDownloader {

	public static void main(String[] args) throws IOException, InterruptedException {
		var day = AoCUtils.getDay();

		var basePath = Path.of(System.getProperty("user.dir"));

		var javaBasePath = basePath.resolve("src/main/java/com/nightwolf");
		var javaDayDirPath = javaBasePath.resolve("day%02d".formatted(day));
		if (!Files.exists(javaDayDirPath)) {
			Files.createDirectory(javaDayDirPath);
		}

		var resourceBasePath = basePath.resolve("src/main/resources/com/nightwolf");
		var resourceDayPath = resourceBasePath.resolve("day%02d".formatted(day));
		if (!Files.exists(resourceDayPath)) {
			Files.createDirectory(resourceDayPath);
		}

		var client = AoCUtils.getClient();

		//		var challengeHtmlPath = resourceDayPath.resolve("challenge.html");
		//		if (!Files.exists(challengeHtmlPath)) {
		//			var challengeTextRequest = getChallengeTextRequest(day);
		//			var challengeHtml = client.send(challengeTextRequest, HttpResponse.BodyHandlers.ofString());
		//			Files.writeString(challengeHtmlPath, challengeHtml.body());
		//		}

		var challengeInputPath = resourceDayPath.resolve("input.txt");
		if (!Files.exists(challengeInputPath)) {
			var challengeInputRequest = getChallengeInputRequest(day);
			var challengeInput = client.send(challengeInputRequest, HttpResponse.BodyHandlers.ofString());
			Files.writeString(challengeInputPath, challengeInput.body());
		}
	}

	//	private static HttpRequest getChallengeTextRequest(int day) {
	//		return HttpRequest.newBuilder()
	//				.GET()
	//				.uri(URI.create("https://adventofcode.com/2022/day/" + day))
	//				.build();
	//	}

	private static HttpRequest getChallengeInputRequest(int day) {
		return HttpRequest.newBuilder()
				.GET()
				.uri(URI.create("https://adventofcode.com/2022/day/" + day + "/input"))
				.build();
	}

}
