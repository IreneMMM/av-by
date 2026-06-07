package by.av.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

	private final By loginByEmailButton = By.xpath("//button[contains(text(),\"почте или логину\")]");
	private final By emailOrLoginInput = By.xpath("//input[@id=\"authLogin\"]");
	private final By passwordInput = By.xpath("//input[@id=\"loginPassword\"]");
	private final By showPasswordButton = By.xpath("//label[@for=\"loginPassword\"]/button");
	private final By forgetPasswordButton = By.xpath("//button[contains(text(), \"Не помню пароль\")]");
	private final By submitButton = By.xpath("//button[@class=\"button button--action\"]");
	private final By errorMessage = By.xpath("//div[@class=\"error-message\"]");
	private final By userMenu = By.xpath("//ul[@class=\"nav__personal\"]");
	private final By recoveryPasswordTitle = By.xpath("//div[contains(text(),\"Запрос на восстановление пароля\")]");
	private final By recoveryPasswordSubmitButton = By.xpath("//div[@aria-labelledby=\"почте\"]//button[contains(text(),\"Отправить\")]");
	private final By recoveryPasswordEmailInput = By.xpath("//input[@id=\"email\"]");
	private final By recoveryPasswordByEmailButton = By.xpath("//div[contains(@class, 'drawer__slide--active')]//button[text()='почте']");

	public LoginPage() {
		super();
	}

	public WebElement getSubmitButton() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton));
	}

	public WebElement getRecoveryPasswordSubmitButton() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryPasswordSubmitButton));
	}

	public void clickLoginByEmailButton() {
		wait.until(ExpectedConditions.elementToBeClickable(loginByEmailButton)).click();
	}

	public void clickRecoveryPasswordByEmailButton() {
		wait.until(ExpectedConditions.elementToBeClickable(recoveryPasswordByEmailButton)).click();
	}

	public void setEmailOrLoginInput(String email) {
		wait.until(ExpectedConditions.elementToBeClickable(emailOrLoginInput)).sendKeys(email);
	}

	public void setPasswordInput(String pass) {
		driver.findElement(passwordInput).sendKeys(pass);
	}

	public void setRecoveryPasswordEmailInput(String email) {
		driver.findElement(recoveryPasswordEmailInput).sendKeys(email);
	}

	public void clickSubmitButton() {
		driver.findElement(submitButton).click();
	}

	public void clickRecoveryPasswordSubmitButton() {
		driver.findElement(recoveryPasswordSubmitButton).click();
	}

	public void clearEmailOrLoginInput() {
		wait.until(ExpectedConditions.elementToBeClickable(emailOrLoginInput)).clear();
	}

	public void clearPasswordInput() {
		wait.until(ExpectedConditions.elementToBeClickable(passwordInput)).clear();
	}

	public void clickForgetPasswordButton() {
		wait.until(ExpectedConditions.elementToBeClickable(forgetPasswordButton)).click();
	}

	public void clickShowPasswordButton() {
		driver.findElement(showPasswordButton).click();
	}

	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
	}

	public String getRecoveryPasswordTitle() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(recoveryPasswordTitle)).getText().trim();
	}
}
