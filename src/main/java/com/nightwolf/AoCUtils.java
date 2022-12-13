package com.nightwolf;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AoCUtils {

	private static final Properties properties;

	static {
		properties = new Properties();
		try {
			properties.load(Leaderboard.class.getResourceAsStream("application.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private AoCUtils() {
	}

	public static String getSessionCookie() {
		return properties.getProperty("cookie.session");
	}

	public static String getLeaderBoardId() {
		return properties.getProperty("leaderboard.id");
	}

	public static int getDay() {
		return Integer.parseInt(properties.getProperty("day", "1"));
	}

	public static HttpClient getClient() {
		return HttpClient.newBuilder().cookieHandler(new CookieHandler() {
			@Override
			public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) {
				return Map.of("Cookie",
						List.of("session=" + AoCUtils.getSessionCookie()));
			}

			@Override
			public void put(URI uri, Map<String, List<String>> responseHeaders) {
			}
		}).build();
	}

}
