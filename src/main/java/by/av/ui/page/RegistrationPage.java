package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {
	private static final Logger log = LogManager.getLogger(RegistrationPage.class);
	private final By registrationButton = By.xpath("//span[contains(text(),\"Регистрация\")]");
	private final By registrationByEmailButton = By.xpath("//div[@class=\"drawer__slide drawer__slide--active\"]" + "//button[contains(text(),\"почте\")]");
	private final By nameInput = By.xpath("//input[@id=\"name\"]");
	private final By emailInput = By.xpath("//input[@id=\"regEmail\"]");
	private final By passwordInput = By.xpath("//input[@id=\"regPassword\"]");
	private final By submitButton = By.xpath("//form[.//input[@id='regEmail']]//button[normalize-space()='Зарегистрироваться']");
	private final By emailSubmitTitle = By.xpath("//div[contains(text(), \"Подтверждение почтового адреса\")]");
	private final By errorMessageNameInput = By.xpath("//input[@id='name']/following-sibling::div[@class='error-message']");
	private final By errorMessageEmailInput = By.xpath("//input[@id='regEmail']/following-sibling::div[@class='error-message']");
	private final By errorMessagePasswordInput = By.xpath("//input[@id='regPassword']/following-sibling::div[@class='error-message']");

	private final String errorMessageNameIsNotCyrillic = "Напишите имя кириллицей";
	private final String errorMessageNameIsTooShort = "Слишком короткое имя";
	private final String errorMessageEmailInvalid = "Введите почту полностью. Например, info@av.by";
	private final String errorMessagePasswordInvalidLength = "Минимум 8 символов";
	private final String errorMessagePasswordInvalidChars = "В пароле должны быть цифры и латинские буквы";

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

	//IN PROGRESS
	@Step("Get registration name error message")
	public String getNameErrorMessage() {
		String message = driver.findElement(errorMessageNameInput).getText();
		log.info("Registration name error message: {}", message);
		return message;
	}

	@Step("Get registration email error message")
	public String getEmailErrorMessage() {
		String message = driver.findElement(errorMessageEmailInput).getText();
		log.info("Registration email error message: {}", message);
		return message;
	}

	@Step("Get registration password error message")
	public String getPasswordErrorMessage() {
		String message = driver.findElement(errorMessagePasswordInput).getText();
		log.info("Registration password error message: {}", message);
		return message;
	}
}
