package by.av.ui.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchFilterPage extends BasePage {
	private static final Logger log = LogManager.getLogger(SearchFilterPage.class);
	private static final String FILTER_PAGE_URL = "https://cars.av.by/filter";

	private final By brandDropdown = By.xpath("//div[contains(@class,'filter-models')]//button[@title='Марка']");
	private final By showListingsButton = By.xpath("//div[@class='filter__show-result']//button[contains(., 'Показать')]");
	private final By listingTitles = By.xpath("//h3[contains(@class,'listing-item__title')]//span[contains(@class,'link-text')]");
	private final By firstListingTitle = By.xpath("(//h3[contains(@class,'listing-item__title')]//span[contains(@class,'link-text')])[1]");
	private final By resultsOverlay = By.xpath("//div[contains(@class,'overlay--animated') and not(@hidden)]");

	public SearchFilterPage() {
		super();
	}

	public void open() {
		log.info("Opening search filter page: {}", FILTER_PAGE_URL);
		driver.get(FILTER_PAGE_URL);
	}

	public void selectBrand(String brand) {
		log.info("Selecting brand: {}", brand);
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(brandDropdown));
		dropdown.click();

		By brandOption = By.xpath(
				"//div[contains(@class,'filter-models')]//button[contains(@class,'dropdown__listbutton') and normalize-space(text())='"
						+ brand + "']");
		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(brandOption));
		option.click();
	}

	public void clickShowListingsButton() {
		log.info("Clicking show listings button");
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(showListingsButton));
		button.click();
	}

	public void waitForResultsLoaded(String brand) {
		log.info("Waiting for filtered listings with brand: {}", brand);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(resultsOverlay));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(firstListingTitle, brand));
	}

	public List<String> getListingTitles() {
		List<String> titles = driver.findElements(listingTitles).stream()
				.map(element -> element.getText().trim())
				.filter(title -> !title.isEmpty())
				.toList();
		log.info("Found {} listing titles", titles.size());
		return titles;
	}
}
