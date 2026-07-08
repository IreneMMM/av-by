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

	public LoginPage() {
		super();
	}

	private static String maskPassword(String password) {
		if (password == null) {
			return "null";
		}
		int len = password.length();
		if (len <= 2) {
			return "*".repeat(len);
		}
		return password.charAt(0) + "*".repeat(len - 2) + password.charAt(len - 1);
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
		log.info("Entered login: {}", email);
	}

	@Step("Enter password")
	public void setPasswordInput(String pass) {
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).sendKeys(pass);
		log.info("Entered password: {} (len={})", maskPassword(pass), pass == null ? 0 : pass.length());
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
	public boolean waitForLoginFormToClose() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(emailOrLoginInput));
			log.info("Login drawer closed (email input is not visible)");
			return true;
		} catch (org.openqa.selenium.TimeoutException e) {
			String error = getErrorMessageIfPresent();
			if (error != null && !error.isBlank()) {
				log.error("Login failed inside drawer. Error message: {}", error);
				return false;
			}
			throw e;
		}
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

	@Step("Try get login error message if present")
	public String getErrorMessageIfPresent() {
		try {
			WebElement el = driver.findElement(errorMessage);
			if (el.isDisplayed()) {
				String message = el.getText().trim();
				log.info("Login error message if present: {}", message);
				return message;
			}
		} catch (org.openqa.selenium.NoSuchElementException ignored) {
			// ok - error message not present
		}
		return null;
	}

	@Step("Get recovery password title")
	public String getRecoveryPasswordTitle() {
		String title = wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryPasswordTitle)).getText().trim();
		log.info("Recovery password title: {}", title);
		return title;
	}
}
