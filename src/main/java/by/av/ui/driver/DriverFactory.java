package by.av.ui.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	public static WebDriver getDriver(String browser) {
		switch (browser.toLowerCase().trim()) {
			case "chrome":
				return new ChromeDriver();
			case "firefox":
			case "mozilla":
				return new FirefoxDriver();
			default:
				throw new IllegalArgumentException("This browser is not supported: " + browser);
		}
	}
}
