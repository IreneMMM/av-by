package by.av.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {
	private static final String BASE_URL = "https://av.by/";
	private static final String COPYRIGHT_TEXT = "© 2001, ООО «Автоклассифайд», УНП 192787977, Минск, ул. Платонова, 20б, пом. 145";
	private static final List<String> NAVIGATION_ITEMS = List.of(
			"Объявления", "Сервисы", "Журнал", "Знания", "Услуги", "Проверка VIN"
	);

	private final By acceptCookieButton = By.xpath("//button[@class=\"button button--primary button--block button--large\"]");
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


	public HomePage() {
		super();
	}

	public void open() {
		driver.get(BASE_URL);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void acceptCookies() {
		try {
			WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(acceptCookieButton));
			cookieButton.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(acceptCookieButton));
		} catch (Exception ignored) {
		}
	}

	public void clickLoginButton() {
		driver.findElement(loginButton).click();
	}

	public String getExpectedCopyrightText() {
		return COPYRIGHT_TEXT;
	}

	public String getCopyrightText() {
		return driver.findElement(copyrightText).getText();
	}

	public void openListOfThemes() {
		WebElement themeButton = wait.until(ExpectedConditions.presenceOfElementLocated(themeToggleBtn));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", themeButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", themeButton);
	}

	private By getThemeButton(String theme) {
		return switch (theme.toLowerCase()) {
			case "auto" -> autoThemeButton;
			case "light" -> lightThemeButton;
			case "dark" -> darkThemeButton;
			default -> throw new IllegalArgumentException("Unknown theme: " + theme + ". Use: auto, light, dark");
		};
	}

	public void chooseTheme(String theme) {
		By themeButton = getThemeButton(theme);
		openListOfThemes();
		WebElement themeOption = wait.until(ExpectedConditions.elementToBeClickable(themeButton));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", themeOption);
	}

	public boolean isThemeActive(String theme) {
		By themeButton = getThemeButton(theme);
		String ariaPressed = driver.findElement(themeButton).getAttribute("aria-pressed");
		return ariaPressed.equals("true");
	}

	public List<String> getNavigationItems() {
		return NAVIGATION_ITEMS;
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

	public boolean isNavigationItemDisplayed(String item) {
		By locator = getNavItemLocator(item);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
	}

	public void clickVinCheckNavLink(){
		driver.findElement(vinCheckNavLink).click();
	}
}
