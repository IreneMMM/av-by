package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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

	@Step("Open search filter page: " + FILTER_PAGE_URL)
	public void open() {
		driver.get(FILTER_PAGE_URL);
	}

	@Step("Select brand: {brand}")
	public void selectBrand(String brand) {
		clickWhenReady(brandDropdown);

		By brandOption = By.xpath(
				"//div[contains(@class,'filter-models')]//button[contains(@class,'dropdown__listbutton') and normalize-space(text())='"
						+ brand + "']");
		clickWhenReady(brandOption);
	}

	@Step("Click show listings button")
	public void clickShowListingsButton() {
		clickWhenReady(showListingsButton);
	}

	@Step("Wait for filtered listings with brand: {brand}")
	public void waitForResultsLoaded(String brand) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(resultsOverlay));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(firstListingTitle, brand));
	}

	@Step("Get listing titles")
	public List<String> getListingTitles() {
		List<String> titles = driver.findElements(listingTitles).stream()
				.map(element -> element.getText().trim())
				.filter(title -> !title.isEmpty())
				.toList();
		log.info("Found {} listing titles", titles.size());
		return titles;
	}
}
