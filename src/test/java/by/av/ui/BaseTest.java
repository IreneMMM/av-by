package by.av.ui;

import by.av.ui.driver.Driver;
import by.av.ui.page.BasePage;
import by.av.ui.page.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
	protected WebDriver driver;
	protected HomePage homePage;

	protected void initDriver() {
		driver = Driver.getDriver();
	}

	protected void acceptCookies() {
		BasePage.acceptCookiesIfPresent();
	}

	@BeforeEach
	public void setup() {
		initDriver();
		homePage = new HomePage();
		homePage.open();
		acceptCookies();
	}

	@AfterEach
	public void tearDown() {
		Driver.quitDriver();
	}
}
