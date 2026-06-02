package by.av.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

	private final By loginByEmailButton = By.xpath("//button[contains(text(),\"почте или логину\")]");
	private final By emailOrLoginInput = By.xpath("//input[@id=\"authLogin\"]");
	private final By passwordInput = By.xpath("//input[@id=\"loginPassword\"]");
	private final By showPasswordButton = By.xpath("//label[@for=\"loginPassword\"]/button");
	private final By forgetPasswordButton = By.xpath("//button[@class=\"button button--link button--small\"]");
	private final By submitButton = By.xpath("//button[@class=\"button button--action\"]");
	private final By errorMessage = By.xpath("//div[@class=\"error-message\"]");
	private final By userMenu = By.xpath("//ul[@class=\"nav__personal\"]");

	public LoginPage() {
		super();
	}

	public void clickLoginByEmailButton() {
		wait.until(ExpectedConditions.elementToBeClickable(loginByEmailButton)).click();
	}

	public void setEmailOrLoginInput(String email) {
		driver.findElement(emailOrLoginInput).sendKeys(email);
	}

	public void setPasswordInput(String pass) {
		driver.findElement(passwordInput).sendKeys(pass);
	}

	public void clickSubmitButton() {
		driver.findElement(submitButton).click();
	}

	public void clickForgetPasswordButton() {
		driver.findElement(forgetPasswordButton).click();
	}

	public void clickShowPasswordButton() {
		driver.findElement(showPasswordButton).click();
	}

	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
	}

	public boolean isUserMenuDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(userMenu)).isDisplayed();
	}
}
