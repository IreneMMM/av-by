package by.av.ui.data;

import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Random;

public class TestData {
	private static final Logger log = LogManager.getLogger(TestData.class);
	private static final Faker FAKER = new Faker();
	private static final Faker RU_FAKER = new Faker(Locale.forLanguageTag("ru"));
	private static final int PASSWORD_MIN_LENGTH = 0;
	private static final int PASSWORD_MAX_LENGTH = 50;
	private static final int VIN_LENGTH = 17;
	private static final String VIN_ALLOWED_CHARS = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
	private static final int[] PRICE_VARIANTS = {0, 1, 42, 350, 8750, 45009, 125008, 212374, 399999, 400000, 400001};

	public String randomEmail() {
		String email = FAKER.internet().emailAddress();
		log.info("Generated email: {}", email);
		return email;
	}

	public String randomCyrillicName() {
		String name = RU_FAKER.name().firstName();
		log.info("Generated Cyrillic name: {}", name);
		return name;
	}

	public String randomLatinName() {
		String name = FAKER.name().firstName();
		log.info("Generated latin name: {}", name);
		return name;
	}

	public String strictLengthPassword(int length) {
		String password = FAKER.internet().password(length, length);
		log.info("Generated password with length {}: {}", length, password);
		return password;
	}

	public String randomLengthPassword() {
		String password = FAKER.internet().password(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
		log.info("Generated password with length between {} and {}: {}", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH, password);
		return password;
	}

	public String randomVin() {
		Random random = new Random();
		StringBuilder vin = new StringBuilder(VIN_LENGTH);
		for (int i = 0; i < VIN_LENGTH; i++) {
			int charIndex = random.nextInt(VIN_ALLOWED_CHARS.length());
			vin.append(VIN_ALLOWED_CHARS.charAt(charIndex));
		}
		log.info("Generated VIN: {}", vin);
		return vin.toString();
	}

	public int randomMinPrice() {
		Random random = new Random();
		int priceFrom = PRICE_VARIANTS[random.nextInt(PRICE_VARIANTS.length)];
		log.info("Selected random USD minimum price from predefined list: {}", priceFrom);
		return priceFrom;
	}
}
