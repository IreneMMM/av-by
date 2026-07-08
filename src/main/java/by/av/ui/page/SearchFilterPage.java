package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SearchFilterPage extends BasePage {
	private static final Logger log = LogManager.getLogger(SearchFilterPage.class);
	private static final String FILTER_PAGE_URL = "https://cars.av.by/filter";
	private static final Random RANDOM = new Random();
	private static final String ANY_OPTION_TEXT = "Любой";
	private static final Duration LOADER_APPEAR_WAIT = Duration.ofSeconds(5);
	private final String dropdownOptionTemplate = "//*[contains(@class,'dropdown__listbutton')][normalize-space(.)='%s']";

	private final By brandDropdown = By.xpath("//button[@title='Марка']");
	private final By modelDropdown = By.xpath("//button[@title='Модель']");
	private final By yearFromDropdown = By.xpath("//button[@title='Год от']");
	private final By yearToDropdown = By.xpath("//div[@id='p-7-year']//button[@title='до']");
	private final By priceFromInput = By.xpath("//span[text()='Цена от']/following-sibling::input");
	private final By currencyDropdown = By.xpath("//button[@id='p-10-price_currency']");
	private final By showResultsButton = By.xpath("//button[@class='button button--primary button--block']");
	private final By dropdownOptions = By.xpath("//*[contains(@class,'dropdown__listbutton')]");
	private final By resultTitles = By.xpath("//a[@class='listing-item__link']/span");
	private final By firstResultTitle = By.xpath("(//a[@class='listing-item__link']/span)[1]");
	private final By resultYears = By.xpath("//div[contains(@class,'listing-item__params')]/div[1]");
	private final By resultPrices = By.xpath("//div[contains(@class,'listing-item__price-primary')]/span");
	private final By resultPriceCurrencies = By.xpath("//div[contains(@class,'listing-item__price-primary')]/small");

	public SearchFilterPage() {
		super();
	}

	@Step("Open search filter page: " + FILTER_PAGE_URL)
	public void open() {
		driver.get(FILTER_PAGE_URL);
		log.info("Opened search filter page");
	}

	@Step("Select random brand")
	public String selectRandomBrand() {
		String brand = selectRandomDropdownOption(brandDropdown);
		log.info("Selected brand: {}", brand);
		return brand;
	}

	@Step("Select random model")
	public String selectRandomModel() {
		String model = selectRandomDropdownOption(modelDropdown);
		log.info("Selected model: {}", model);
		return model;
	}

	@Step("Select random year from")
	public int selectYearFrom() {
		int yearFrom = Integer.parseInt(selectRandomDropdownOption(yearFromDropdown));
		log.info("Selected year from: {}", yearFrom);
		return yearFrom;
	}

	@Step("Select random year to")
	public int selectYearTo() {
		int yearTo = Integer.parseInt(selectRandomDropdownOption(yearToDropdown));
		log.info("Selected year to: {}", yearTo);
		return yearTo;
	}

	@Step("Select random year to not earlier than {minYear}")
	public int selectYearToAtLeast(int minYear) {
		clickDropdownButton(yearToDropdown);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));
		List<WebElement> options = driver.findElements(dropdownOptions).stream()
				.filter(WebElement::isDisplayed)
				.filter(WebElement::isEnabled)
				.filter(option -> !option.getText().isBlank())
				.filter(option -> !ANY_OPTION_TEXT.equalsIgnoreCase(option.getText().trim()))
				.filter(option -> Integer.parseInt(option.getText().trim()) >= minYear)
				.toList();
		if (options.isEmpty()) {
			throw new TimeoutException("No year-to options found at or above: " + minYear);
		}
		WebElement selectedOption = chooseRandomOption(options);
		String selectedText = selectedOption.getText().trim();
		applyDropdownSelection(yearToDropdown, selectedOption, selectedText);
		int yearTo = Integer.parseInt(selectedText);
		log.info("Selected year to (>= {}): {}", minYear, yearTo);
		return yearTo;
	}

	@Step("Set price from: {price}")
	public void setPriceFrom(String price) {
		setPriceInput(priceFromInput, price);
		log.info("Set minimum price: {}", price);
	}

	@Step("Select currency: {currency}")
	public void selectCurrency(String currency) {
		selectDropdownOption(currencyDropdown, currency);
		log.info("Selected currency: {}", currency);
	}

	@Step("Click show results button")
	public void clickShowResultButton() {
		clickShowResultButtonAndHasResults(true);
		log.info("Clicked show results button");
	}

	@Step("Click show results button and return whether listings were found")
	public boolean clickShowResultButtonAndHasResults() {
		boolean hasResults = clickShowResultButtonAndHasResults(false);
		log.info("Clicked show results button, listings found: {}", hasResults);
		return hasResults;
	}

	private boolean clickShowResultButtonAndHasResults(boolean failIfEmpty) {
		String urlBefore = driver.getCurrentUrl();
		clickWhenReady(showResultsButton);
		wait.until(driver -> !driver.getCurrentUrl().equals(urlBefore));
		waitForLoaderToFinish();
		if (isFirstResultVisible()) {
			return true;
		}
		if (failIfEmpty) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(firstResultTitle));
		}
		return false;
	}

	private boolean isFirstResultVisible() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5))
					.until(ExpectedConditions.visibilityOfElementLocated(firstResultTitle));
			return true;
		} catch (TimeoutException e) {
			log.info("No listings visible for selected filters");
			return false;
		}
	}

	@Step("Wait for filtered results")
	public void waitForResultsLoaded() {
		waitForLoaderToFinish();
		wait.until(ExpectedConditions.visibilityOfElementLocated(firstResultTitle));
		log.info("Results loaded");
	}

	private void waitForLoaderToFinish() {
		By loadingOverlay = By.className("listing__loader");
		try {
			new WebDriverWait(driver, LOADER_APPEAR_WAIT)
					.until(ExpectedConditions.visibilityOfElementLocated(loadingOverlay));
		} catch (TimeoutException ignored) {
			log.debug("Listing loader did not appear");
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
	}

	@Step("Get result titles")
	public List<String> getResultTitles() {
		waitForResultsLoaded();
		List<String> titles = getElementTexts(resultTitles);
		log.info("Found {} result titles", titles.size());
		return titles;
	}

	@Step("Get result prices")
	public List<Integer> getResultPrices() {
		waitForResultsLoaded();
		List<Integer> prices = extractNumbers(resultPrices);
		log.info("Found {} result prices", prices.size());
		return prices;
	}

	@Step("Get result years")
	public List<Integer> getResultYears() {
		waitForResultsLoaded();
		List<Integer> years = extractNumbers(resultYears);
		log.info("Found {} result years", years.size());
		return years;
	}

	@Step("Get result price currencies")
	public List<String> getResultPriceCurrencies() {
		waitForResultsLoaded();
		List<String> currencies = getElementTexts(resultPriceCurrencies);
		log.info("Found {} result currencies", currencies.size());
		return currencies;
	}

	private List<String> getElementTexts(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return wait.ignoring(StaleElementReferenceException.class).until(driver -> {
			List<String> texts = driver.findElements(locator).stream().map(WebElement::getText).toList();
			return texts.isEmpty() ? null : texts;
		});
	}

	private void clickDropdownButton(By dropdownButton) {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(dropdownButton));
		scrollToElement(button);
		jsClick(button);
	}

	private void selectDropdownOption(By dropdownButton, String optionText) {
		clickDropdownButton(dropdownButton);
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(dropdownOptionTemplate, optionText))));
		option.click();
	}

	private String selectRandomDropdownOption(By dropdownButton) {
		List<WebElement> options = loadSelectableDropdownOptions(dropdownButton);
		WebElement selectedOption = chooseRandomOption(options);
		String selectedText = selectedOption.getText().trim();
		applyDropdownSelection(dropdownButton, selectedOption, selectedText);
		return selectedText;
	}

	private List<WebElement> loadSelectableDropdownOptions(By dropdownButton) {
		clickDropdownButton(dropdownButton);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions));
		List<WebElement> options = driver.findElements(dropdownOptions).stream().filter(WebElement::isDisplayed).filter(WebElement::isEnabled).filter(option -> !option.getText().isBlank()).filter(option -> !ANY_OPTION_TEXT.equalsIgnoreCase(option.getText().trim())).toList();
		if (options.isEmpty()) {
			throw new TimeoutException("No selectable options found for dropdown: " + dropdownButton);
		}
		return options;
	}

	private WebElement chooseRandomOption(List<WebElement> options) {
		return options.get(RANDOM.nextInt(options.size()));
	}

	private void applyDropdownSelection(By dropdownButton, WebElement selectedOption, String selectedText) {
		scrollToElement(selectedOption);
		wait.until(ExpectedConditions.elementToBeClickable(selectedOption)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(dropdownOptions));
	}

	private void setPriceInput(By inputLocator, String price) {
		WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
		input.sendKeys(price);
		input.sendKeys(Keys.TAB);
	}

	private List<Integer> extractNumbers(By locator) {
		return getElementTexts(locator).stream().map(text -> Integer.parseInt(text.replaceAll("\\D+", ""))).toList();
	}
}
