package by.av.ui.page;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
	private static final Logger log = LogManager.getLogger(LoginPage.class);

	private final By loginByEmailButton = By.xpath("//button[contains(text(),\"почте или логину\")]");
	private final By emailOrLoginInput = By.xpath("//input[@id=\"authLogin\"]");
	private final By passwordInput = By.xpath("//input[@id=\"loginPassword\"]");
	private final By forgetPasswordButton = By.xpath("//button[contains(text(), \"Не помню пароль\")]");
	private final By submitButton = By.xpath("//button[@class=\"button button--action\"]");
	private final By errorMessage = By.xpath("//div[@class=\"error-message\"]");
	private final By recoveryPasswordTitle = By.xpath("//div[contains(text(),\"Запрос на восстановление пароля\")]");
	private final By recoveryPasswordSubmitButton = By.xpath("//div[@aria-labelledby=\"почте\"]//button[contains(text(),\"Отправить\")]");
	private final By recoveryPasswordEmailInput = By.xpath("//input[@id=\"email\"]");
	private final By recoveryPasswordByEmailButton = By.xpath("//div[contains(@class, 'drawer__slide--active')]//button[text()='почте']");

	public LoginPage() {
		super();
	}

	public WebElement getSubmitButton() {
		WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton));
		log.info("Login submit button enabled: {}", button.isEnabled());
		return button;
	}

	public WebElement getRecoveryPasswordSubmitButton() {
		WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryPasswordSubmitButton));
		log.info("Recovery password submit button enabled: {}", button.isEnabled());
		return button;
	}

	@Step("Select login by email or login")
	public void clickLoginByEmailButton() {
		wait.until(ExpectedConditions.elementToBeClickable(loginByEmailButton)).click();
	}

	@Step("Select password recovery by email")
	public void clickRecoveryPasswordByEmailButton() {
		clickWhenReady(recoveryPasswordByEmailButton);
	}

	@Step("Enter login: {email}")
	public void setEmailOrLoginInput(String email) {
		wait.until(ExpectedConditions.elementToBeClickable(emailOrLoginInput)).sendKeys(email);
	}

	@Step("Enter password: {pass}")
	public void setPasswordInput(String pass) {
		driver.findElement(passwordInput).sendKeys(pass);
	}

	@Step("Enter recovery email: {email}")
	public void setRecoveryPasswordEmailInput(String email) {
		driver.findElement(recoveryPasswordEmailInput).sendKeys(email);
	}

	@Step("Submit login form")
	public void clickSubmitButton() {
		driver.findElement(submitButton).click();
	}

	@Step("Clear login input")
	public void clearEmailOrLoginInput() {
		wait.until(ExpectedConditions.elementToBeClickable(emailOrLoginInput)).clear();
	}

	@Step("Clear password input")
	public void clearPasswordInput() {
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).clear();
	}

	@Step("Open password recovery form")
	public void clickForgetPasswordButton() {
		wait.until(ExpectedConditions.elementToBeClickable(forgetPasswordButton)).click();
	}

	@Step("Get login error message")
	public String getErrorMessage() {
		String message = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
		log.info("Login error message: {}", message);
		return message;
	}

	@Step("Get recovery password title")
	public String getRecoveryPasswordTitle() {
		String title = wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryPasswordTitle)).getText().trim();
		log.info("Recovery password title: {}", title);
		return title;
	}
}
