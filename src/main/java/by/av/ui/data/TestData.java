package by.av.ui.data;

import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class TestData {
	private static final Logger LOG = LogManager.getLogger(TestData.class);
	private static final Faker FAKER = new Faker();
	private static final int PASSWORD_MIN_LENGTH = 0;
	private static final int PASSWORD_MAX_LENGTH = 50;
	private static final int VIN_LENGTH = 17;
	private static final String VIN_ALLOWED_CHARS = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
	private static final String[] CAR_BRANDS = {
			"Abarth", "Acura", "Aito", "Alfa Romeo", "Alpina", "ARO", "Asia", "Aston Martin", "Audi", "Avatr",
			"BAIC", "BAW", "Belgee", "Bentley", "BMW", "Buick", "BYD", "Cadillac", "Changan", "Chery",
			"Chevrolet", "Chrysler", "Citroen", "Cupra", "Dacia", "Daewoo", "Daihatsu", "Datsun", "Denza", "Derways",
			"DFSK", "Dodge", "Dongfeng", "Dongfeng Honda", "DS", "Epai", "EXEED", "FangChengBao", "Farizon", "FAW",
			"Ferrari", "Fiat", "Fisker", "Ford", "Foton", "GAC", "Geely", "Genesis", "GMC", "Great Wall",
			"Hafei", "Haima", "Haval", "Hedmos", "HiPhi", "Honda", "Hongqi", "Hongxing", "Hozon", "Hummer",
			"Hycan", "Hyundai", "Infiniti", "Iran Khodro", "Isuzu", "IVECO", "JAC", "Jaguar", "Jeep", "Jetour",
			"Jetta", "Jiangling", "Jmev", "Kaiyi", "Karma", "Kia", "Lada (ВАЗ)", "Lamborghini", "Lancia", "Land Rover",
			"Leapmotor", "Lexus", "Li Auto", "Lifan", "Lincoln", "Lingbao", "Lingxi", "Livan", "Lotus", "Lucid",
			"Lynk & Co", "Maserati", "Maxus", "Mazda", "Mercedes-Benz", "Mercury", "MG", "MHERO", "MINI", "Mitsubishi",
			"Nio", "Nissan", "Oldsmobile", "Omoda", "Opel", "Ora", "Oting", "Peugeot", "Plymouth", "Polar",
			"Polestar", "Pontiac", "Porsche", "Proton", "RAM", "Ravon", "Renault", "Renault Samsung", "Rivian", "Roewe",
			"Rolls-Royce", "Rover", "Rox", "Saab", "Saipa", "Santana", "Saturn", "Scion", "SEAT", "SERES",
			"Shenlan (Deepal)", "Shineray", "Skoda", "Skywell", "Smart", "SsangYong", "Subaru", "Suzuki", "Tank", "Tata",
			"Tesla", "Toyota", "Volkswagen", "Volvo", "Vortex", "Voyah", "Wartburg", "Weltmeister", "Wuling", "Xiaomi",
			"Xpeng", "Zeekr", "Zotye", "ZX", "Богдан", "ГАЗ", "ЕрАЗ", "ЗАЗ", "ИЖ", "ЛуАЗ",
			"Москвич", "ТагАЗ", "УАЗ", "Эксклюзив"
	};

	public String randomEmail() {
		String email = FAKER.internet().emailAddress();
		LOG.info("Generated email: {}", email);
		return email;
	}

	public String strictLengthPassword(int length) {
		String password = FAKER.internet().password(length, length);
		LOG.info("Generated password with length {}: {}", length, password);
		return password;
	}

	public String rangeLengthPassword() {
		String password = FAKER.internet().password(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
		LOG.info("Generated password with length between {} and {}: {}", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH, password);
		return password;
	}

	public String randomVin() {
		Random random = new Random();
		StringBuilder vin = new StringBuilder(VIN_LENGTH);
		for (int i = 0; i < VIN_LENGTH; i++) {
			int charIndex = random.nextInt(VIN_ALLOWED_CHARS.length());
			vin.append(VIN_ALLOWED_CHARS.charAt(charIndex));
		}
		LOG.info("Generated VIN: {}", vin);
		return vin.toString();
	}

	public String randomCarBrand() {
		String brand = CAR_BRANDS[new Random().nextInt(CAR_BRANDS.length)];
		LOG.info("Generated car brand: {}", brand);
		return brand;
	}
}
