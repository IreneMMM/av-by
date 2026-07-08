package by.av.ui;

import by.av.ui.data.TestData;
import by.av.ui.page.SearchFilterPage;
import by.av.ui.service.CurrencyRateProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

import static by.av.ui.assertions.SearchFilterAssertions.*;

public class SearchFilterPageTest extends BaseTest {
	private static final Logger log = LogManager.getLogger(SearchFilterPageTest.class);
	private static final int MAX_COMBINED_FILTER_ATTEMPTS = 5;
	private static final int MIN_STABLE_PRICE_BYN = 2000;
	SearchFilterPage searchFilterPage;
	CurrencyRateProvider currencyRateProvider;
	TestData testData;

	@BeforeEach
	@Override
	public void setup(TestInfo testInfo) {
		super.setup(testInfo);
		searchFilterPage = new SearchFilterPage();
		currencyRateProvider = new CurrencyRateProvider();
		testData = new TestData();
		searchFilterPage.open();
		acceptCookies();
	}

	@DisplayName("Check result titles contain selected brand after filter")
	@Test
	public void testResultTitlesContainSelectedBrand() {
		String brand = searchFilterPage.selectRandomBrand();
		log.info("Selected brand: {}", brand);
		searchFilterPage.clickShowResultButton();
		searchFilterPage.waitForResultsLoaded();

		List<String> resultTitles = searchFilterPage.getResultTitles();
		assertResultsNotEmpty(resultTitles, "brand filter");
		log.info("Received {} titles for brand check", resultTitles.size());
		assertTitlesContainBrand(resultTitles, brand);
	}

	@DisplayName("Check result titles contain selected model after filter")
	@Test
	public void testResultTitlesContainSelectedModel() {
		searchFilterPage.selectRandomBrand();
		String model = searchFilterPage.selectRandomModel();
		log.info("Selected model: {}", model);
		searchFilterPage.clickShowResultButton();
		searchFilterPage.waitForResultsLoaded();

		List<String> resultTitles = searchFilterPage.getResultTitles();
		assertResultsNotEmpty(resultTitles, "model filter");
		log.info("Received {} titles for model check", resultTitles.size());
		assertTitlesContainModel(resultTitles, model);
	}

	@DisplayName("Check result years are within selected year range")
	@Test
	public void testResultYearsWithinSelectedRange() {
		int yearFrom = searchFilterPage.selectYearFrom();
		int yearTo = searchFilterPage.selectYearToAtLeast(yearFrom);

		log.info("Selected year range: {}-{}", yearFrom, yearTo);
		searchFilterPage.clickShowResultButton();

		List<Integer> resultYears = searchFilterPage.getResultYears();
		assertResultsNotEmpty(resultYears, "year filter");
		log.info("Received {} years for range check", resultYears.size());
		assertYearsWithinRange(resultYears, yearFrom, yearTo);
	}

	@DisplayName("Check result prices are above selected minimum price in USD")
	@Test
	public void testResultPricesAboveMinimumInUsd() {
		int randomMinPrice = testData.randomMinPrice();
		double minPriceInByn = currencyRateProvider.getConvertedBynForUsd(randomMinPrice);
		log.info("Min price in BYN = {}", minPriceInByn);
		searchFilterPage.open();
		acceptCookies();
		searchFilterPage.selectCurrency("USD");
		searchFilterPage.setPriceFrom(String.valueOf(randomMinPrice));
		searchFilterPage.clickShowResultButton();
		List<Integer> resultPricesByn = searchFilterPage.getResultPrices();
		assertResultsNotEmpty(resultPricesByn, "price filter");
		log.info("Received {} prices for minimum price check", resultPricesByn.size());

		assertPricesAboveMinimum(resultPricesByn, minPriceInByn, randomMinPrice);
	}

	@DisplayName("Check Results are displayed in BYN currency")
	@Test
	public void testResultsDisplayedInByn() {
		searchFilterPage.selectCurrency("BYN");
		searchFilterPage.clickShowResultButton();

		List<String> currencies = searchFilterPage.getResultPriceCurrencies();
		assertResultsNotEmpty(currencies, "currency filter");
		log.info("Received {} currencies for BYN check", currencies.size());
		assertCurrenciesContainByn(currencies);
	}

	@DisplayName("Check combined filters work with BYN currency")
	@Test
	public void testCombinedFiltersWithBynCurrency() {
		String brand = null;
		String model = null;
		int yearFrom = 0;
		boolean resultsFound = false;

		for (int attempt = 1; attempt <= MAX_COMBINED_FILTER_ATTEMPTS; attempt++) {
			if (attempt > 1) {
				searchFilterPage.open();
				acceptCookies();
			}

			brand = searchFilterPage.selectRandomBrand();
			model = searchFilterPage.selectRandomModel();
			yearFrom = searchFilterPage.selectYearFrom();
			searchFilterPage.selectCurrency("BYN");
			searchFilterPage.setPriceFrom(String.valueOf(MIN_STABLE_PRICE_BYN));


			log.info("Combined filters attempt {}/{}: brand='{}', model='{}', yearFrom={}, minStablePriceByn={}",
					attempt, MAX_COMBINED_FILTER_ATTEMPTS, brand, model, yearFrom, MIN_STABLE_PRICE_BYN);

			if (searchFilterPage.clickShowResultButtonAndHasResults()) {
				resultsFound = true;
				break;
			}
		}

		Assumptions.assumeTrue(resultsFound,
				"No results found after " + MAX_COMBINED_FILTER_ATTEMPTS + " combined filter attempts (skip)");

		final String selectedBrand = brand;
		final String selectedModel = model;
		final int selectedYearFrom = yearFrom;

		List<String> resultTitles = searchFilterPage.getResultTitles();
		List<Integer> resultYears = searchFilterPage.getResultYears();
		List<Integer> resultPrices = searchFilterPage.getResultPrices();
		List<String> resultCurrencies = searchFilterPage.getResultPriceCurrencies();

		Assertions.assertAll(
				() -> assertResultsNotEmpty(resultTitles, "combined filters (titles)"),
				() -> assertResultsNotEmpty(resultYears, "combined filters (years)"),
				() -> assertResultsNotEmpty(resultPrices, "combined filters (prices)"),
				() -> assertResultsNotEmpty(resultCurrencies, "combined filters (currencies)")
		);

		log.info("Received {} titles, {} years, {} prices and {} currencies for combined filter check",
				resultTitles.size(), resultYears.size(), resultPrices.size(), resultCurrencies.size());

		Assertions.assertAll(
				() -> assertTitlesContainBrand(resultTitles, selectedBrand),
				() -> assertTitlesContainModel(resultTitles, selectedModel),
				() -> assertYearsNotBelowMinimum(resultYears, selectedYearFrom),
				() -> assertPricesNotBelowMinimumByn(resultPrices, MIN_STABLE_PRICE_BYN),
				() -> assertCurrenciesContainByn(resultCurrencies)
		);
	}
}
