package by.av.ui.page;

import by.av.ui.driver.Driver;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
	protected static final Duration DEFAULT_WAIT = Duration.ofSeconds(10);
	private static final Logger log = LogManager.getLogger(BasePage.class);
	private static final By ACCEPT_COOKIE_BUTTON = By.xpath("//button[@class=\"button button--primary button--block button--large\"]");

	protected WebDriver driver;
	protected WebDriverWait wait;

	protected BasePage() {
		this.driver = Driver.getDriver();
		this.wait = new WebDriverWait(driver, DEFAULT_WAIT);
	}

	@Step("Accept cookies if present")
	public static void acceptCookiesIfPresent() {
		WebDriver driver = Driver.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
		try {
			WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_COOKIE_BUTTON));
			cookieButton.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(ACCEPT_COOKIE_BUTTON));
			log.info("Cookies accepted");
		} catch (TimeoutException ignored) {
			log.debug("Cookie banner not displayed");
		}
	}

	protected void scrollToElement(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}

	protected void jsClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	protected void clickWhenReady(By locator) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		scrollToElement(element);
		jsClick(element);
	}
}
