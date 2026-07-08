package by.av.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class EnvConfig {
	public static final String VALID_LOGIN = "VALID_LOGIN";
	public static final String VALID_PASSWORD = "VALID_PASSWORD";

	private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

	private EnvConfig() {
	}

	public static String get(String key) {
		String envValue = System.getenv(key);
		if (envValue != null && !envValue.isBlank()) {
			return envValue;
		}
		return DOTENV.get(key);
	}

	public static String require(String key) {
		String value = get(key);
		if (value == null || value.isBlank()) {
			throw new IllegalStateException(
					"Missing required value: " + key + ". Set environment variable or add it to .env file.");
		}
		return value;
	}
}
