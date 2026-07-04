package by.av.ui.page;

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
	private final By showPasswordButton = By.xpath("//label[@for=\"loginPassword\"]/button");
	private final By forgetPasswordButton = By.xpath("//button[contains(text(), \"Не помню пароль\")]");
	private final By submitButton = By.xpath("//button[@class=\"button button--action\"]");
	private final By errorMessage = By.xpath("//div[@class=\"error-message\"]");
	//private final By userMenu = By.xpath("//ul[@class=\"nav__personal\"]");
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

	public void clickLoginByEmailButton() {
		log.info("Selecting login by email or login");
		wait.until(ExpectedConditions.elementToBeClickable(loginByEmailButton)).click();
	}

	public void clickRecoveryPasswordByEmailButton() {
		log.info("Selecting password recovery by email");
		wait.until(ExpectedConditions.elementToBeClickable(recoveryPasswordByEmailButton)).click();
	}

	public void setEmailOrLoginInput(String email) {
		log.info("Entering login: {}", email);
		wait.until(ExpectedConditions.elementToBeClickable(emailOrLoginInput)).sendKeys(email);
	}

	public void setPasswordInput(String pass) {
		log.info("Entering password: {}", pass);
		driver.findElement(passwordInput).sendKeys(pass);
	}

	public void setRecoveryPasswordEmailInput(String email) {
		log.info("Entering recovery email: {}", email);
		driver.findElement(recoveryPasswordEmailInput).sendKeys(email);
	}

	public void clickSubmitButton() {
		log.info("Submitting login form");
		driver.findElement(submitButton).click();
	}

	public void clickRecoveryPasswordSubmitButton() {
		log.info("Submitting recovery password form");
		driver.findElement(recoveryPasswordSubmitButton).click();
	}

	public void clearEmailOrLoginInput() {
		log.info("Clearing login input");
		wait.until(ExpectedConditions.elementToBeClickable(emailOrLoginInput)).clear();
	}

	public void clearPasswordInput() {
		log.info("Clearing password input");
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).clear();
	}

	public void clickForgetPasswordButton() {
		log.info("Opening password recovery form");
		wait.until(ExpectedConditions.elementToBeClickable(forgetPasswordButton)).click();
	}

	public void clickShowPasswordButton() {
		log.info("Toggling password visibility");
		driver.findElement(showPasswordButton).click();
	}

	public String getErrorMessage() {
		String message = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
		log.info("Login error message: {}", message);
		return message;
	}

	public String getRecoveryPasswordTitle() {
		String title = wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryPasswordTitle)).getText().trim();
		log.info("Recovery password title: {}", title);
		return title;
	}
}
