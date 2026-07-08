package by.av.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvConfig {
	public static final String VALID_LOGIN = "VALID_LOGIN";
	public static final String VALID_PASSWORD = "VALID_PASSWORD";

	private static final Dotenv DOTENV = Dotenv.load();

	private EnvConfig() {
	}

	public static String get(String key) {
		return DOTENV.get(key);
	}

	public static String require(String key) {
		String value = DOTENV.get(key);
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("Missing required value in .env: " + key);
		}
		return value;
	}
}
