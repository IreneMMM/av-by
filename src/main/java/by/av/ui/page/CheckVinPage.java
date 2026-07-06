package by.av.ui.page;

import by.av.ui.driver.Driver;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckVinPage extends BasePage {
	private static final Logger log = LogManager.getLogger(CheckVinPage.class);
	private static final String CHECK_VIN_PAGE_URL = "https://av.by/vin";
	private static final String EXAMPLE_REPORT_URL = "https://av.by/vin/example";

	private final By vinInput = By.xpath("//div[@class=\"vin-main__content\"]//input");
	private final By checkVinButton = By.xpath("//button[contains(text(),\"Проверить VIN\")]");
	private final By vinErrorMessage = By.xpath("//div[@class=\"error-message\"]");
	private final By whereFindVinButton = By.xpath("//button[contains(text(),\"Где найти VIN\")]");
	private final By exampleReportLink = By.xpath("//div[@class=\"vin-main__content\"]//a[contains(text(),\"Пример отчёта\")]");
	private final By whereFindVinTitle = By.xpath("//div[@class=\"modal__dialog modal__dialog--find-vin\"]//div[@class=\"modal__title\"]");
	public CheckVinPage() {
		super();
	}

	@Step("Open VIN check page: " + CHECK_VIN_PAGE_URL)
	public void openVinPage() {
		driver.get(CHECK_VIN_PAGE_URL);
	}

	public String getExampleReportUrl() {
		return EXAMPLE_REPORT_URL;
	}

	@Step("Wait for pre-report page")
	public String waitForPreReportPage() {
		wait.until(ExpectedConditions.urlContains("/vin/prereport/"));
		String currentUrl = Driver.getDriver().getCurrentUrl();
		log.info("Pre-report page URL: {}", currentUrl);
		return currentUrl;
	}

	@Step("Submit VIN check")
	public void clickCheckVinButton() {
		wait.until(ExpectedConditions.elementToBeClickable(checkVinButton)).click();
	}

	@Step("Enter VIN: {vinCode}")
	public void fillVinInput(String vinCode) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(vinInput)).sendKeys(vinCode);
	}

	@Step("Open VIN example report")
	public void clickExampleReportLink() {
		String originalWindow = driver.getWindowHandle();
		wait.until(ExpectedConditions.elementToBeClickable(exampleReportLink)).click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(originalWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}
		wait.until(ExpectedConditions.urlContains("/vin/example"));
		log.info("Example report URL: {}", driver.getCurrentUrl());
	}

	@Step("Open 'where find VIN' image")
	public void openImageWhereFindVin() {
		wait.until(ExpectedConditions.elementToBeClickable(whereFindVinButton)).click();
	}

	@Step("Get VIN error message")
	public String getTextVinErrorMessage() {
		String message = wait.until(ExpectedConditions.visibilityOfElementLocated(vinErrorMessage)).getText();
		log.info("VIN error message: {}", message);
		return message;
	}

	@Step("Get 'where find VIN' title")
	public String getTextWhereFindVinTitle() {
		String title = wait.until(ExpectedConditions.visibilityOfElementLocated(whereFindVinTitle)).getText();
		log.info("'Where find VIN' title: {}", title);
		return title;
	}
}
