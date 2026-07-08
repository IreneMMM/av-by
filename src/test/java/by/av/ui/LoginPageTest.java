package by.av.ui;

import by.av.config.EnvConfig;
import by.av.ui.data.TestData;
import by.av.ui.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class LoginPageTest extends BaseTest {
	private static final String INVALID_CREDENTIALS_MESSAGE =
			"Неверный логин или пароль. Если забыли пароль, восстановите его";
	private static final String INVALID_EMAIL_FORMAT_MESSAGE =
			"Укажите адрес электронной почты корректного формата. Рекомендуем русскую раскладку";
	private static final String TOO_MANY_ATTEMPTS_MESSAGE =
			"Слишком часто вводится неверный пароль. Повторите попытку позже";

	LoginPage loginPage;
	TestData testData;

	@BeforeEach
	@Override
	public void setup(TestInfo testInfo) {
		super.setup(testInfo);
		openHomePage();
		homePage.clickLoginButton();
		loginPage = new LoginPage();
		testData = new TestData();
	}

	@DisplayName("Check login with valid credentials opens user navigation")
	@Test
	public void testLoginWithValidCredentials() {
		loginPage.loginAndSubmitForm(
				EnvConfig.require(EnvConfig.VALID_LOGIN),
				EnvConfig.require(EnvConfig.VALID_PASSWORD));

		loginPage.waitForLoginFormToClose();

		Assertions.assertTrue(
				homePage.isUserLoggedIn(),
				"User is not logged in after login with valid credentials");
	}

	@DisplayName("Check login with invalid email or login")
	@Test
	public void testLoginWithInvalidEmailOrLogin() {
		String emailOrLogin = testData.randomEmail().replace("@", "");
		String password = testData.strictLengthPassword(10);

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);
		loginPage.clickSubmitButton();

		String actualErrorMessage = loginPage.getErrorMessage();
		Assertions.assertTrue(
				INVALID_EMAIL_FORMAT_MESSAGE.equals(actualErrorMessage)
						|| actualErrorMessage.contains("корректного формата")
						|| INVALID_CREDENTIALS_MESSAGE.equals(actualErrorMessage)
						|| TOO_MANY_ATTEMPTS_MESSAGE.equals(actualErrorMessage),
				"Unexpected login error message: " + actualErrorMessage);
	}

	@DisplayName("Check login with invalid password")
	@Test
	public void testLoginWithInvalidPassword() {
		String emailOrLogin = testData.randomEmail();
		String password = testData.strictLengthPassword(6);

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);
		loginPage.clickSubmitButton();

		String actualErrorMessage = loginPage.getErrorMessage();
		Assertions.assertTrue(
				INVALID_CREDENTIALS_MESSAGE.equals(actualErrorMessage)
						|| TOO_MANY_ATTEMPTS_MESSAGE.equals(actualErrorMessage),
				"Unexpected login error message: " + actualErrorMessage);
	}
	@DisplayName("Check submit button is disabled with empty credentials")
	@Test
	public void testSubmitButtonIsDisableWithEmptyCredentials() {
		loginPage.clickLoginByEmailButton();
		Assertions.assertFalse(loginPage.getSubmitButton().isEnabled());
	}

	@DisplayName("Check submit button is disabled with empty email or login")
	@Test
	public void testSubmitButtonIsDisableWithEmptyEmailOrLogin() {
		String password = testData.strictLengthPassword(6);

		loginPage.clickLoginByEmailButton();
		loginPage.setPasswordInput(password);
		Assertions.assertFalse(loginPage.getSubmitButton().isEnabled());
	}

	@DisplayName("Check submit button is disabled with empty password")
	@Test
	public void testSubmitButtonIsDisableWithEmptyPassword() {
		String emailOrLogin = testData.randomEmail();

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		Assertions.assertFalse(loginPage.getSubmitButton().isEnabled());
	}

	@DisplayName("Check forget password button")
	@Test
	public void testForgetPasswordButton() {
		loginPage.clickLoginByEmailButton();
		loginPage.clickForgetPasswordButton();

		String actualTitle = loginPage.getRecoveryPasswordTitle();
		String expectedTitle = "Запрос на восстановление пароля";
		Assertions.assertAll(
				() -> Assertions.assertEquals(expectedTitle, actualTitle),
				() -> Assertions.assertFalse(loginPage.getRecoveryPasswordSubmitButton().isEnabled())
		);
	}

	@DisplayName("Check recovery password submit button is enabled")
	@Test
	public void testRecoveryPasswordSubmitButtonIsEnabled() {
		String email = testData.randomEmail();
		loginPage.clickLoginByEmailButton();
		loginPage.clickForgetPasswordButton();
		loginPage.clickRecoveryPasswordByEmailButton();
		loginPage.setRecoveryPasswordEmailInput(email);
		Assertions.assertTrue(loginPage.getRecoveryPasswordSubmitButton().isEnabled());
	}
}
