package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
	private static final Logger log = LogManager.getLogger(HomePage.class);
	private static final String BASE_URL = "https://av.by/";

	private final By copyrightText = By.xpath("//div[@class='footer__copy']");
	private final By adsNavLink = By.xpath("//nav//a[.//span[text()='Объявления']]");
	private final By servicesNavLink = By.xpath("//nav//a[.//span[text()='Сервисы']]");
	private final By newsNavLink = By.xpath("//a[@href=\"/news\"]/span");
	private final By knowledgeNavLink = By.xpath("//nav//a[.//span[text()='Знания']]");
	private final By paidServicesNavLink = By.xpath("//nav//a[.//span[text()='Услуги']]");
	private final By vinCheckNavLink = By.xpath("//li[contains(@class,'nav__item--alt')]/a");
	private final By loginButton = By.xpath("//li[contains(@class,\"nav__item--login\")]/a");
	private final By submitAdButton = By.xpath("//li[contains(@class,\"nav__item--add\")]/button");
	private final By themeToggleBtn = By.xpath("//button[contains(@class,\"theme__control\")]");
	private final By darkThemeButton = By.xpath("//button[contains(@class,\"theme__button--dark\")]");
	private final By lightThemeButton = By.xpath("//button[contains(@class,\"theme__button--light\")]");
	private final By autoThemeButton = By.xpath("//button[contains(@class,\"theme__button--auto\")]");
	private final By loginSlider = By.xpath("//div[@class=\"drawer__slide drawer__slide--active\"]");
	private final By loginNavItem = By.xpath("//li[contains(@class,'nav__item--login')]");

	public HomePage() {
		super();
	}

	@Step("Open homepage: " + BASE_URL)
	public void open() {
		driver.get(BASE_URL);
	}

	@Step("Refresh homepage")
	public void refreshPage() {
		driver.navigate().refresh();
	}

	@Step("Open login form")
	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}

	@Step("Get copyright text")
	public String getCopyrightText() {
		String text = driver.findElement(copyrightText).getText();
		log.info("Footer copyright text: {}", text);
		return text;
	}

	public void openListOfThemes() {
		WebElement themeButton = wait.until(ExpectedConditions.presenceOfElementLocated(themeToggleBtn));
		scrollToElement(themeButton);
		jsClick(themeButton);
	}

	private By getThemeButton(String theme) {
		return switch (theme.toLowerCase()) {
			case "auto" -> autoThemeButton;
			case "light" -> lightThemeButton;
			case "dark" -> darkThemeButton;
			default -> throw new IllegalArgumentException("Unknown theme: " + theme + ". Use: auto, light, dark");
		};
	}

	@Step("Choose theme: {theme}")
	public void chooseTheme(String theme) {
		By themeButton = getThemeButton(theme);
		openListOfThemes();
		WebElement themeOption = wait.until(ExpectedConditions.elementToBeClickable(themeButton));
		jsClick(themeOption);
	}

	@Step("Check theme is active: {theme}")
	public boolean isThemeActive(String theme) {
		By themeButton = getThemeButton(theme);
		String ariaPressed = driver.findElement(themeButton).getAttribute("aria-pressed");
		boolean active = ariaPressed.equals("true");
		log.info("Theme '{}' active: {}", theme, active);
		return active;
	}

	private By getNavItemLocator(String item) {
		return switch (item.toLowerCase()) {
			case "объявления" -> adsNavLink;
			case "сервисы" -> servicesNavLink;
			case "журнал" -> newsNavLink;
			case "знания" -> knowledgeNavLink;
			case "услуги" -> paidServicesNavLink;
			case "проверка vin" -> vinCheckNavLink;
			default -> throw new IllegalArgumentException("Unknown navigation item: " + item);
		};
	}

	@Step("Check navigation item displayed: {item}")
	public boolean isNavigationItemDisplayed(String item) {
		By locator = getNavItemLocator(item);
		boolean displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
		log.info("Navigation item '{}' displayed: {}", item, displayed);
		return displayed;
	}

	@Step("Navigate to VIN check page")
	public void clickVinCheckNavLink() {
		driver.findElement(vinCheckNavLink).click();
	}

	@Step("Click submit ad button")
	public void clickSubmitAdButton() {
		clickWhenReady(submitAdButton);
	}

	@Step("Check login slider is opened")
	public boolean isLoginSliderOpened() {
		boolean isOpened = wait.until(ExpectedConditions.visibilityOfElementLocated(loginSlider)).isDisplayed();
		log.info("Login slider opened: {}", isOpened);
		return isOpened;
	}

	@Step("Check user is logged in")
	public boolean isUserLoggedIn() {
		boolean isLoggedIn = wait.until(ExpectedConditions.invisibilityOfElementLocated(loginNavItem));
		log.info("User logged in: {}", isLoggedIn);
		return isLoggedIn;
	}
}
