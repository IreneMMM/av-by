package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {
	private static final Logger log = LogManager.getLogger(RegistrationPage.class);
	public static final String ERROR_MESSAGE_NAME_NOT_CYRILLIC = "Напишите имя кириллицей";
	public static final String ERROR_MESSAGE_NAME_TOO_SHORT = "Слишком короткое имя";
	public static final String ERROR_MESSAGE_EMAIL_INVALID = "Введите почту полностью. Например, info@av.by";
	public static final String ERROR_MESSAGE_PASSWORD_INVALID_LENGTH = "Минимум 8 символов";
	public static final String ERROR_MESSAGE_PASSWORD_INVALID_CHARS = "В пароле должны быть цифры и латинские буквы";
	private final By emailForm = By.xpath("//form[.//input[@id='regEmail']]");
	private final By registrationButton = By.xpath("//span[contains(text(),\"Регистрация\")]");
	private final By registrationByEmailButton = By.xpath("//div[@class=\"drawer__slide drawer__slide--active\"]" + "//button[contains(text(),\"почте\")]");
	private final By nameInput = By.xpath("//input[@id=\"name\"]");
	private final By emailInput = By.xpath("//input[@id=\"regEmail\"]");
	private final By passwordInput = By.xpath("//input[@id=\"regPassword\"]");
	private final By submitButton = By.xpath("//form[.//input[@id='regEmail']]//button[normalize-space()='Зарегистрироваться']");
	private final By emailSubmitTitle = By.xpath("//div[contains(text(), \"Подтверждение почтового адреса\")]");
	private final By emailSubmitMessage = By.xpath("//p[contains(text(), 'Мы отправили письмо')]");

	public RegistrationPage() {
		super();
	}

	@Step("Open registration form")
	public void clickRegistrationButton() {
		wait.until(ExpectedConditions.elementToBeClickable(registrationButton)).click();
	}

	@Step("Select registration by email")
	public void clickRegistrationByEmail() {
		WebElement emailTab = wait.until(ExpectedConditions.presenceOfElementLocated(registrationByEmailButton));
		scrollToElement(emailTab);
		jsClick(emailTab);
		wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
	}

	@Step("Enter registration name: {name}")
	public void setNameInput(String name) {
		wait.until(ExpectedConditions.elementToBeClickable(nameInput)).sendKeys(name);
	}

	@Step("Enter registration email: {email}")
	public void setEmailInput(String email) {
		wait.until(ExpectedConditions.elementToBeClickable(emailInput)).sendKeys(email);
	}

	@Step("Enter registration password: {pass}")
	public void setPasswordInput(String pass) {
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(pass);
	}

	@Step("Open registration form by email")
	public void getRegistrationForm() {
		clickRegistrationButton();
		clickRegistrationByEmail();
	}

	@Step("Submit registration form")
	public void clickSubmitButton() {
		wait.until(ExpectedConditions.presenceOfElementLocated(submitButton));
		((JavascriptExecutor) driver).executeScript(
				"document.querySelector('form input#regEmail').closest('form').requestSubmit();");
	}

	@Step("Check if reCAPTCHA challenge is blocking registration")
	public boolean isCaptchaChallengeBlocking() {
		return driver.findElements(By.cssSelector("iframe[src*='recaptcha/api2/bframe']"))
				.stream()
				.anyMatch(WebElement::isDisplayed);
	}

	@Step("Get email confirmation title")
	public String getEmailSubmitTitle() {
		WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(30));
		String title = extendedWait.until(ExpectedConditions.visibilityOfElementLocated(emailSubmitTitle)).getText();
		log.info("Registration confirmation title: {}", title);
		return title;
	}

	@Step("Get email confirmation message")
	public String getEmailSubmitMessage() {
		String message = wait.until(ExpectedConditions.visibilityOfElementLocated(emailSubmitMessage)).getText();
		log.info("Registration confirmation message: {}", message);
		return message;
	}

	@Step("Get registration name error message")
	public String getNameErrorMessage() {
		return getValidationMessageByText(ERROR_MESSAGE_NAME_NOT_CYRILLIC, ERROR_MESSAGE_NAME_TOO_SHORT);
	}

	@Step("Get registration email error message")
	public String getEmailErrorMessage() {
		return getValidationMessageByText(ERROR_MESSAGE_EMAIL_INVALID);
	}

	@Step("Get registration password error message")
	public String getPasswordErrorMessage() {
		return getValidationMessageByText(ERROR_MESSAGE_PASSWORD_INVALID_LENGTH, ERROR_MESSAGE_PASSWORD_INVALID_CHARS);
	}

	private String getValidationMessageByText(String... expectedTexts) {
		return wait.until(driver -> {
			String formText = driver.findElement(emailForm).getText();
			for (String expectedText : expectedTexts) {
				if (formText.contains(expectedText)) {
					log.info("Validation message found: {}", expectedText);
					return expectedText;
				}
			}
			return null;
		});
	}
}
