package by.av.ui.page;

import by.av.ui.driver.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
	private final int DEFAULT_WAIT = 10;

	protected WebDriver driver;
	protected WebDriverWait wait;

	protected BasePage() {
		this.driver = Driver.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
	}
}
