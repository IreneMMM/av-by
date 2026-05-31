package by.av.ui.page;

import by.av.ui.driver.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
	protected final Duration DEFAULT_WAIT = Duration.ofSeconds(10);

	protected WebDriver driver;
	protected WebDriverWait wait;

	protected BasePage() {
		this.driver = Driver.getDriver();
		this.wait = new WebDriverWait(driver, DEFAULT_WAIT);
	}
}
