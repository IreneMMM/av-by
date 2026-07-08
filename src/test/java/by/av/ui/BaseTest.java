package by.av.ui;

import by.av.ui.driver.Driver;
import by.av.ui.page.BasePage;
import by.av.ui.page.HomePage;
import by.av.ui.page.RegistrationPage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
	private static final Logger log = LogManager.getLogger(BaseTest.class);

	protected WebDriver driver;
	protected HomePage homePage;
	private String testName;

	protected void initDriver() {
		driver = Driver.getDriver();
	}

	@Step("Accept cookies")
	protected void acceptCookies() {
		BasePage.acceptCookiesIfPresent();
	}

	@Step("Open homepage")
	protected void openHomePage() {
		homePage = new HomePage();
		homePage.open();
		acceptCookies();
	}

	@BeforeEach
	public void setup(TestInfo testInfo) {
		testName = testInfo.getDisplayName();
		log.info("Starting UI test: {}", testName);
		initDriver();
	}

	@AfterEach
	public void tearDown() {
		log.info("Finishing UI test: {}", testName);
		Driver.quitDriver();
	}

	protected void skipIfCaptchaAppears(RegistrationPage registrationPage) {
		Assumptions.assumeFalse(
				registrationPage.isCaptchaAppears(),
				"Registration blocked by reCAPTCHA challenge");
	}
}
