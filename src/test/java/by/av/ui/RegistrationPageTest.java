package by.av.ui;

import by.av.ui.page.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class RegistrationPageTest extends BaseTest {
	RegistrationPage registrationPage;

	@BeforeEach
	@Override
	public void setup(TestInfo testInfo) {
		super.setup(testInfo);
		openHomePage();
		homePage.clickLoginButton();
		registrationPage = new RegistrationPage();
	}

	@DisplayName("Check submission code is sent")
	@Test
	public void testSubmissionCodeIsSent() {
		String name = "Тест";
		String email = "акауa@mail.com";
		String password = "qwerty@147852";

		registrationPage.getRegistrationForm();
		registrationPage.fillRegistrationForm(name, email, password);
		registrationPage.clickSubmitButton();

		//иногда вылазит капча, как обойти?
		String actualSubmitTitle = registrationPage.getEmailSubmitTitle();
		Assertions.assertEquals("Подтверждение почтового адреса", actualSubmitTitle);
	}
}
