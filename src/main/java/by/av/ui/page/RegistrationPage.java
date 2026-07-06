package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
		wait.until(ExpectedConditions.elementToBeClickable(registrationByEmailButton)).click();
	}
	
	@Step("Enter registration name: {name}")
	public void setNameInput(String name) {
		driver.findElement(nameInput).sendKeys(name);
	}
	
	@Step("Enter registration email: {email}")
	public void setEmailInput(String email) {
		driver.findElement(emailInput).sendKeys(email);
	}
	
	@Step("Enter registration password: {pass}")
	public void setPasswordInput(String pass) {
		driver.findElement(passwordInput).sendKeys(pass);
	}
	
	@Step("Open registration form by email")
	public void getRegistrationForm() {
		clickRegistrationButton();
		clickRegistrationByEmail();
	}
	
	@Step("Fill registration form: name={name}, email={email}, password={pass}")
	public void fillRegistrationForm(String name, String email, String pass) {
		setNameInput(name);
		setEmailInput(email);
		setPasswordInput(pass);
	}
	
	@Step("Submit registration form")
	public void clickSubmitButton() {
		clickWhenReady(submitButton);
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
			for (String expectedText : expectedTexts) {
				try {
					By locator = By.xpath("//*[normalize-space(.)='" + expectedText + "']");
					WebElement element = driver.findElement(locator);
					if (element.isDisplayed()) {
						String foundText = element.getText();
						log.info("Validation message found: {}", foundText);
						return foundText;
					}
				} catch (Exception ignored) {
				}
			}
			return null;
		});
	}
}


