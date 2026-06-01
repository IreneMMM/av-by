package by.av.ui;

import by.av.ui.page.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegistrationPageTest extends BaseTest {
	@DisplayName("Check submission code is sent")
	@Test
	public void testSubmissionCodeIsSent() {
		String name = "Тест";
		String email = "акауa@mail.com";
		String password = "qwerty@147852";

		homePage.clickLoginButton();

		RegistrationPage registrationPage = new RegistrationPage();

		registrationPage.clickRegistrationButton();
		registrationPage.clickRegistrationByEmail();
		registrationPage.setNameInput(name);
		registrationPage.setEmailInput(email);
		registrationPage.setPasswordInput(password);
		registrationPage.clickSubmitButton();

		String actualSubmitTitle = registrationPage.getEmailSubmitTitle();
		Assertions.assertEquals("Подтверждение почтового адреса", actualSubmitTitle);
	}
}
