package by.av.ui.page;

import by.av.ui.driver.Driver;
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
	private final By whereFindVinCloseButton = By.xpath("//div[div[text()=\"Где найти VIN\"]]//button[@class=\"modal__close\"]");

    public CheckVinPage() {
		super();
	}

	public void openVinPage() {
		log.info("Opening VIN check page: {}", CHECK_VIN_PAGE_URL);
		driver.get(CHECK_VIN_PAGE_URL);
	}

	public String getExampleReportUrl() {
		return EXAMPLE_REPORT_URL;
	}

	public String waitForPreReportPage() {
		wait.until(ExpectedConditions.urlContains("/vin/prereport/"));
		String currentUrl = Driver.getDriver().getCurrentUrl();
		log.info("Pre-report page opened: {}", currentUrl);
		return currentUrl;
	}

	public void clickCheckVinButton() {
		log.info("Submitting VIN check");
		wait.until(ExpectedConditions.elementToBeClickable(checkVinButton)).click();
	}

	public void fillVinInput(String vinCode) {
		log.info("Entering VIN: {}", vinCode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(vinInput)).sendKeys(vinCode);
	}

	//можно ли по-другому обработать открытие ссылки в новом окне?
	public void clickExampleReportLink() {
		log.info("Opening VIN example report");
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
		log.info("Example report opened: {}", driver.getCurrentUrl());
	}

	public void openImageWhereFindVin() {
		log.info("Opening 'where find VIN' image");
		wait.until(ExpectedConditions.elementToBeClickable(whereFindVinButton)).click();
	}

	public void closeImageWhereFindVin() {
		log.info("Closing 'where find VIN' image");
		driver.findElement(whereFindVinCloseButton).click();
	}

	public String getTextVinErrorMessage() {
		String message = wait.until(ExpectedConditions.visibilityOfElementLocated(vinErrorMessage)).getText();
		log.info("VIN error message: {}", message);
		return message;
	}

	public String getTextWhereFindVinTitle() {
		String title = wait.until(ExpectedConditions.visibilityOfElementLocated(whereFindVinTitle)).getText();
		log.info("'Where find VIN' title: {}", title);
		return title;
	}
}
