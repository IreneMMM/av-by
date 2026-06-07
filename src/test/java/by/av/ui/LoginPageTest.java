package by.av.ui;

import by.av.ui.page.HomePage;
import by.av.ui.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest extends BaseTest {
	HomePage homePage;
	LoginPage loginPage;

	@BeforeEach
	public void setup() {
		homePage = new HomePage();
		homePage.open();
		homePage.clickLoginButton();
		loginPage = new LoginPage();
	}

	@DisplayName("Check submit button is enabled with valid credentials")
	@Test
	public void testSubmitButtonIsEnabledWithValidCredentials() {
		String emailOrLogin = "test@gmail.com";
		String password = "test@109900";

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);

		Assertions.assertTrue(loginPage.getSubmitButton().isEnabled());
	}


	@DisplayName("Check login with invalid email or login")
	@Test
	public void testLoginWithInvalidEmailOrLogin() {
		String emailOrLogin = "testgmail.com";
		String password = "test@109900";

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);
		loginPage.clickSubmitButton();

		String actualErrorMessage = loginPage.getErrorMessage();
		String expectedErrorMessage = "Неверный логин или пароль. Если забыли пароль, восстановите его";

		Assertions.assertEquals(expectedErrorMessage,actualErrorMessage);
	}

	@DisplayName("Check login with invalid password")
	@Test
	public void testLoginWithInvalidPassword() {
		String emailOrLogin = "test@gmail.com";
		String password = "t12123";

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);
		loginPage.clickSubmitButton();

		String actualErrorMessage = loginPage.getErrorMessage();
		String expectedErrorMessage = "Неверный логин или пароль. Если забыли пароль, восстановите его";

		Assertions.assertEquals(expectedErrorMessage,actualErrorMessage);
	}

	@DisplayName("Check submit button is disabled with empty credentials")
	@Test
	public void testSubmitButtonIsDisableWithEmptyCredentials() {
		loginPage.clickLoginByEmailButton();
		loginPage.clearEmailOrLoginInput();
		loginPage.clearPasswordInput();
		Assertions.assertFalse(loginPage.getSubmitButton().isEnabled());
	}

	@DisplayName("Check submit button is disabled with empty email or login")
	@Test
	public void testSubmitButtonIsDisableWithEmptyEmailOrLogin() {
		String password = "t12123";

		loginPage.clickLoginByEmailButton();
		loginPage.clearEmailOrLoginInput();
		loginPage.setPasswordInput(password);
		Assertions.assertFalse(loginPage.getSubmitButton().isEnabled());
	}

	@DisplayName("Check submit button is disabled with empty password")
	@Test
	public void testSubmitButtonIsDisableWithEmptyPassword() {
		String emailOrLogin = "dfg";

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.clearPasswordInput();
		Assertions.assertFalse(loginPage.getSubmitButton().isEnabled());
	}

	@DisplayName("Check forget password button")
	@Test
	public void testForgetPasswordButton() {
		loginPage.clickLoginByEmailButton();
		loginPage.clickForgetPasswordButton();

		String actualTitle = loginPage.getRecoveryPasswordTitle();
		String expectedTitle = "Запрос на восстановление пароля";
		assertAll(
				() -> assertEquals(expectedTitle, actualTitle),
				() -> assertFalse(loginPage.getRecoveryPasswordSubmitButton().isEnabled())
		);
	}

	@DisplayName("Check recovery password submit button is enabled")
	@Test
	public void testRecoveryPasswordSubmitButtonIsEnabled() {
		String email = "test@gmail.com";
		loginPage.clickLoginByEmailButton();
		loginPage.clickForgetPasswordButton();
		loginPage.clickRecoveryPasswordByEmailButton();
		loginPage.setRecoveryPasswordEmailInput(email);
		Assertions.assertTrue(loginPage.getRecoveryPasswordSubmitButton().isEnabled());
	}
}
