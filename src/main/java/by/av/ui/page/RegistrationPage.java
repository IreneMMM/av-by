package by.av.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage {
	private final By registrationButton = By.xpath("//span[contains(text(),\"Регистрация\")]");
	private final By registrationByEmail = By.xpath("//div[@class=\"drawer__slide drawer__slide--active\"]" + "//button[contains(text(),\"почте\")]");
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

	public void clickRegistrationButton() {
		WebElement regButton = wait.until(ExpectedConditions.elementToBeClickable(registrationButton));
		regButton.click();
	}

	public void clickRegistrationByEmail() {
		driver.findElement(registrationByEmail).click();
	}

	public void setNameInput(String name) {
		driver.findElement(nameInput).sendKeys(name);
	}

	public void setEmailInput(String email) {
		driver.findElement(emailInput).sendKeys(email);
	}

	public void setPasswordInput(String pass) {
		driver.findElement(passwordInput).sendKeys(pass);
	}

	public void clickSubmitButton() {
		driver.findElement(submitButton).click();
	}

	public String getEmailSubmitTitle() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(emailSubmitTitle)).getText();
	}

	//IN PROGRESS
	public String getNameErrorMessage() {
		return driver.findElement(errorMessageNameInput).getText();
	}

	public String getEmailErrorMessage() {
		return driver.findElement(errorMessageEmailInput).getText();
	}

	public String getPasswordErrorMessage() {
		return driver.findElement(errorMessagePasswordInput).getText();
	}
}
