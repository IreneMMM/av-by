package by.av.ui.assertions;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class SearchFilterAssertions {

	@Step("Assert all titles contain selected brand: {brand}")
	public static void assertTitlesContainBrand(List<String> resultTitles, String brand) {
		for (String title : resultTitles) {
			Assertions.assertTrue(
					title.contains(brand),
					"Result title does not contain brand '" + brand + "': " + title
			);
		}
	}

	@Step("Assert all titles contain selected model: {model}")
	public static void assertTitlesContainModel(List<String> resultTitles, String model) {
		for (String title : resultTitles) {
			Assertions.assertTrue(
					title.contains(model),
					"Result title does not contain model '" + model + "': " + title
			);
		}
	}

	@Step("Assert all years are within range {yearFrom}-{yearTo}")
	public static void assertYearsWithinRange(List<Integer> resultYears, int yearFrom, int yearTo) {
		for (Integer year : resultYears) {
			Assertions.assertTrue(
					year >= yearFrom && year <= yearTo,
					"Result year " + year + " is outside range " + yearFrom + "-" + yearTo
			);
		}
	}

	@Step("Assert all years are not below {yearFrom}")
	public static void assertYearsNotBelowMinimum(List<Integer> resultYears, int yearFrom) {
		for (Integer year : resultYears) {
			Assertions.assertTrue(
					year >= yearFrom,
					"Result year " + year + " is below selected year from " + yearFrom
			);
		}
	}

	@Step("Assert all prices are above minimum {minPriceUsd} USD")
	public static void assertPricesAboveMinimum(List<Integer> resultPricesByn, double minPriceInByn, int minPriceUsd) {
		int minPriceInBynRoundedDown = (int) Math.floor(minPriceInByn);
		for (Integer bynPrice : resultPricesByn) {
			Assertions.assertTrue(
					bynPrice >= minPriceInBynRoundedDown,
					"Result price " + bynPrice + " BYN is below minimum "
							+ minPriceInByn + " BYN (from " + minPriceUsd + " USD)"
			);
		}
	}

	@Step("Assert all prices are above minimum {minPriceByn} BYN")
	public static void assertPricesNotBelowMinimumByn(List<Integer> resultPricesByn, int minPriceByn) {
		for (Integer bynPrice : resultPricesByn) {
			Assertions.assertTrue(
					bynPrice >= minPriceByn,
					"Result price " + bynPrice + " BYN is below minimum " + minPriceByn + " BYN"
			);
		}
	}

	@Step("Assert all currencies are BYN")
	public static void assertCurrenciesContainByn(List<String> currencies) {
		for (String currency : currencies) {
			Assertions.assertTrue(
					currency.toLowerCase().contains("руб"),
					"Result currency does not contain BYN marker: " + currency
			);
		}
	}

	@Step("Assert results list is not empty for: {filterName}")
	public static void assertResultsNotEmpty(List<?> results, String filterName) {
		Assertions.assertFalse(results.isEmpty(), "No results found after applying " + filterName);
	}
}
