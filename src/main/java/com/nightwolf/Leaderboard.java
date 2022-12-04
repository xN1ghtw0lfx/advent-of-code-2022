package com.nightwolf;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONObject;

public class Leaderboard {
	public static void main(String[] args) throws IOException, InterruptedException {
		var httpClient = AoCUtils.getClient();
		var request = getRequest();
		var json = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

		var context = JsonPath.parse(json);
		var members = context.read("$.members.*", JSONObject[].class);
		for (JSONObject member : members) {
			System.out.println(member.get("name"));
			var completionDayLevel = (JSONObject) member.get("completion_day_level");
			for (int i = 1; i <= 25; i++) {
				var day = (JSONObject) completionDayLevel.get(Integer.toString(i));
				if (day != null) {
					System.out.printf("Day %d%n", i);
					for (int j = 1; j <= 2; j++) {
						var star = (JSONObject) day.get(Integer.toString(j));
						if (star == null) {
							continue;
						}
						var time = (Integer) star.get("get_star_ts") * 1000L;
						var localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.systemDefault());
						System.out.printf("Star %d: %s%n", j, localDateTime);
					}
				}
			}
			System.out.println();
		}
	}

	private static HttpRequest getRequest() {
		return HttpRequest.newBuilder()
				.GET()
				.uri(URI.create("https://adventofcode.com/2022/leaderboard/private/view/%s.json".formatted(AoCUtils.getLeaderBoardId())))
				.build();
	}
}
