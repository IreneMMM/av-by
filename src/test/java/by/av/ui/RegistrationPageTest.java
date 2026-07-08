package by.av.ui;

import by.av.ui.data.TestData;
import by.av.ui.page.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class RegistrationPageTest extends BaseTest {
	RegistrationPage registrationPage;
	TestData testData;

	@BeforeEach
	@Override
	public void setup(TestInfo testInfo) {
		super.setup(testInfo);
		openHomePage();
		homePage.clickLoginButton();
		registrationPage = new RegistrationPage();
		testData = new TestData();
	}

	@DisplayName("Check email confirmation screen after successful registration")
	@Test
	public void testEmailConfirmationScreenAfterSuccessfulRegistration() {
		String email = testData.randomEmail();
		registrationPage.submitRegistrationForm(
				testData.randomCyrillicName(), email, testData.randomLengthPassword());

		skipIfCaptchaAppears(registrationPage);

		String actualSubmitTitle = registrationPage.getEmailSubmitTitle();
		String actualSubmitMessage = registrationPage.getEmailSubmitMessage();
		
		Assertions.assertAll(
				() -> Assertions.assertEquals("Подтверждение почтового адреса", actualSubmitTitle),
				() -> Assertions.assertTrue(
						actualSubmitMessage.contains(email),
						"Confirmation message does not contain email: " + email)
		);
	}

	@DisplayName("Check error message if name is not Cyrillic")
	@Test
	public void testErrorMessageIfNameIsNotCyrillic() {
		registrationPage.submitRegistrationForm(
				testData.randomLatinName(), testData.randomEmail(), testData.randomLengthPassword());

		Assertions.assertEquals(
				RegistrationPage.ERROR_MESSAGE_NAME_NOT_CYRILLIC,
				registrationPage.getNameErrorMessage());
	}

	@DisplayName("Check errors for registration with invalid credentials")
	@Test
	public void testErrorsForRegistrationWithInvalidCredentials() {
		registrationPage.submitRegistrationForm("Я", "testmail.com", "Qwe12");

		Assertions.assertAll(
				() -> Assertions.assertEquals(
						RegistrationPage.ERROR_MESSAGE_NAME_TOO_SHORT,
						registrationPage.getNameErrorMessage()),
				() -> Assertions.assertEquals(
						RegistrationPage.ERROR_MESSAGE_EMAIL_INVALID,
						registrationPage.getEmailErrorMessage()),
				() -> Assertions.assertEquals(
						RegistrationPage.ERROR_MESSAGE_PASSWORD_INVALID_LENGTH,
						registrationPage.getPasswordErrorMessage())
		);
	}

	@DisplayName("Check error message if password does not contain latin letters")
	@Test
	public void testErrorMessageIfPasswordHasNoLatinLetters() {
		registrationPage.submitRegistrationForm(
				testData.randomCyrillicName(), testData.randomEmail(), "12345678");

		Assertions.assertEquals(
				RegistrationPage.ERROR_MESSAGE_PASSWORD_INVALID_CHARS,
				registrationPage.getPasswordErrorMessage());
	}
}
