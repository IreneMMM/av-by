package by.av.ui.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Driver {
	private static final Logger log = LogManager.getLogger(Driver.class);
	private static WebDriver driver;

	private Driver() {}

	public static WebDriver getDriver() {
		if(driver == null) {
			log.info("Initializing WebDriver");
			driver = DriverFactory.getDriver();
		}
		return driver;
	}

	public static void quitDriver() {
		if(driver != null) {
			log.info("Closing WebDriver");
			driver.quit();
			driver = null;
		}
	}
}
