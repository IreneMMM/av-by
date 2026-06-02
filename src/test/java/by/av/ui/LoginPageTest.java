package by.av.ui;

import by.av.ui.page.LoginPage;
import by.av.ui.page.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginPageTest extends BaseTest {
	@DisplayName("Check login with valid credentials")
	@Test
	public void testLoginWithValidCredentials() {
		String emailOrLogin = "tt4999241@gmail.com";
		String password = "test@1000";

		homePage.clickLoginButton();

		LoginPage loginPage = new LoginPage();

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);
		loginPage.clickSubmitButton();

		Assertions.assertTrue(loginPage.isUserMenuDisplayed(), "User menu is not displayed");
	}
}
