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
	private final By emailOrLoginInput = By.xpath("//div[contains(@class,'drawer__slide--active')]//input[@id='authLogin']");
	private final By passwordInput = By.xpath("//div[contains(@class,'drawer__slide--active')]//input[@id='loginPassword']");
	private final By forgetPasswordButton = By.xpath("//div[contains(@class,'drawer__slide--active')]//button[contains(text(), 'Не помню пароль')]");
	private final By submitButton = By.xpath("//div[contains(@class,'drawer__slide--active')]//button[@class='button button--action']");
	private final By errorMessage = By.xpath("//div[contains(@class,'drawer__slide--active')]//div[@class='error-message']");
	private final By recoveryPasswordTitle = By.xpath("//div[contains(text(),\"Запрос на восстановление пароля\")]");
	private final By recoveryPasswordSubmitButton = By.xpath("//div[@aria-labelledby=\"почте\"]//button[contains(text(),\"Отправить\")]");
	private final By recoveryPasswordEmailInput = By.xpath("//input[@id=\"email\"]");
	private final By recoveryPasswordByEmailButton = By.xpath("//div[contains(@class, 'drawer__slide--active')]//button[text()='почте']");
	private final By activeLoginDrawer = By.xpath("//div[contains(@class,'drawer__slide--active')]");

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
		clickWhenReady(loginByEmailButton);
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
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(pass);
	}

	@Step("Enter recovery email: {email}")
	public void setRecoveryPasswordEmailInput(String email) {
		wait.until(ExpectedConditions.elementToBeClickable(recoveryPasswordEmailInput)).sendKeys(email);
	}

	@Step("Submit login form")
	public void clickSubmitButton() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
		scrollToElement(button);
		button.click();
	}

	@Step("Login with credentials: login={login}")
	public void loginAndSubmitForm(String login, String password) {
		clickLoginByEmailButton();
		setEmailOrLoginInput(login);
		setPasswordInput(password);
		clickSubmitButton();
	}

	@Step("Wait until login form is closed")
	public void waitForLoginFormToClose() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(activeLoginDrawer));
		log.info("Login drawer closed");
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
		clickWhenReady(forgetPasswordButton);
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
