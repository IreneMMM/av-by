package by.av.ui;

import by.av.ui.page.HomePage;
import by.av.ui.page.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginPageTest extends BaseTest {
	HomePage homePage;
	LoginPage loginPage;

	@BeforeEach
	@Override
	public void setup() {
		homePage = new HomePage();
		homePage.open();
		homePage.clickLoginButton();
		loginPage = new LoginPage();
	}

	@DisplayName("Check login with valid credentials")
	@Test
	public void testLoginWithValidCredentials() {
		String emailOrLogin = "test@gmail.com";
		String password = "test@109900";

		loginPage.clickLoginByEmailButton();
		loginPage.setEmailOrLoginInput(emailOrLogin);
		loginPage.setPasswordInput(password);
		loginPage.clickSubmitButton();

		Assertions.assertTrue(loginPage.isUserMenuDisplayed(), "User menu is not displayed");
	}
}
