package by.av.ui.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	public static WebDriver getDriver() {
		String browser = System.getProperty("browser", "mozilla").toLowerCase();
		log.info("Opening browser: {}", browser);

		WebDriver webDriver;
		if ("chrome".equals(browser)) {
			webDriver = new ChromeDriver();
		} else if ("edge".equals(browser)) {
			webDriver = new EdgeDriver();
		} else if ("firefox".equals(browser) || "mozilla".equals(browser)) {
			webDriver = new FirefoxDriver();
		} else {
			throw new IllegalArgumentException("This browser is not supported: " + browser);
		}

		webDriver.manage().window().maximize();
		return webDriver;
	}
}
