package by.av.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
	private static final String BASE_URL = "https://av.by/";

	private final By acceptCookieButton = By.xpath("//button[@class=\"button button--primary button--block button--large\"]");
	private final By copyrightText = By.xpath("//div[@class='footer__copy']");
	private final By newsNavLink = By.xpath("//a[@href=\"/news\"]/span");
	private final By vinCheckNavLink = By.xpath("//li[contains(@class=\"nav__item--alt\")]/a");
	private final By loginButton = By.xpath("//li[contains(@class,\"nav__item--login\")]/a");
	private final By submitAdButton = By.xpath("//li[contains(@class,\"nav__item--add\")]/button");
	private final By themeToggleBtn = By.xpath("//button[contains(@class,\"theme__control\")]");
	private final By darkThemeButton = By.xpath("//button[contains(@class,\"theme__button--dark\")]");
	private final By lightThemeButton = By.xpath("//button[contains(@class,\"theme__button--light\")]");
	private final By autoThemeButton = By.xpath("//button[contains(@class,\"theme__button--auto\")]");


	public HomePage() {
		super();
	}

	public void open() {
		driver.get(BASE_URL);
	}

	public void acceptCookies() {
		WebDriverWait cookieWait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			WebElement cookieBtn = cookieWait.until(ExpectedConditions.elementToBeClickable(acceptCookieButton));
			cookieBtn.click();
			cookieWait.until(ExpectedConditions.invisibilityOfElementLocated(acceptCookieButton));
		} catch (Exception ignored) {
		}
	}

	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}

	public String getCopyrightText() {
		return driver.findElement(copyrightText).getText();
	}

	public void openListOfThemes() {
		WebDriverWait wait5sec = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement themeBtn = wait5sec.until(ExpectedConditions.presenceOfElementLocated(themeToggleBtn));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", themeBtn);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", themeBtn);
	}

	private By getThemeButton(String theme) {
		return switch (theme.toLowerCase()) {
			case "auto" -> autoThemeButton;
			case "light" -> lightThemeButton;
			case "dark" -> darkThemeButton;
			default -> throw new IllegalArgumentException("Unknown theme: " + theme + ". Use: auto, light, dark");
		};
	}

	public String chooseTheme(String theme) {
		By themeButton = getThemeButton(theme);

		openListOfThemes();
		WebElement themeOption = wait.until(ExpectedConditions.presenceOfElementLocated(themeButton));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", themeOption);
		return theme.toLowerCase();
	}

	public boolean isThemeActive(String theme) {
		By themeButton = getThemeButton(theme);
		String ariaPressed = driver.findElement(themeButton).getAttribute("aria-pressed");

		if (ariaPressed.equals("true")) {
			return true;
		}
		return false;
	}
}
