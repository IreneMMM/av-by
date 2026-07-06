package by.av.ui;

import by.av.ui.data.TestData;
import by.av.ui.page.RegistrationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
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

	@DisplayName("Check submission code is sent")
	@Test
	public void testSubmissionCodeIsSent() {
		String name = testData.randomCyrillicName();
		String email = testData.randomEmail();
		String password = testData.randomLengthPassword();

		registrationPage.getRegistrationForm();
		registrationPage.setNameInput(name);
		registrationPage.setEmailInput(email);
		registrationPage.setPasswordInput(password);
		registrationPage.clickSubmitButton();

		Assumptions.assumeFalse(
				registrationPage.isCaptchaChallengeBlocking(),
				"Registration blocked by reCAPTCHA challenge");

		String actualSubmitTitle = registrationPage.getEmailSubmitTitle();
		String actualSubmitMessage = registrationPage.getEmailSubmitMessage();
		
		Assertions.assertAll(
				() -> Assertions.assertEquals("Подтверждение почтового адреса", actualSubmitTitle),
				() -> Assertions.assertTrue(
						actualSubmitMessage.contains(email),
						"Confirmation message does not contain email: " + email)
		);
	}

	@DisplayName("Check registration name is Cyrillic")
	@Test
	public void testNameIsCyrillic() {
		registrationPage.getRegistrationForm();
		registrationPage.setNameInput("Rick");
		registrationPage.setEmailInput("testmail.com");
		registrationPage.setPasswordInput("Qwe12");
		registrationPage.clickSubmitButton();

		Assertions.assertEquals(
				RegistrationPage.ERROR_MESSAGE_NAME_NOT_CYRILLIC,
				registrationPage.getNameErrorMessage());
	}

	@DisplayName("Check registration form validation with invalid data")
	@Test
	public void testRegistrationFormValidation() {
		String invalidName = "Я";
		String invalidEmail = "testmail.com";
		String invalidPassword = "Qwe12";
		
		registrationPage.getRegistrationForm();
		registrationPage.setNameInput(invalidName);
		registrationPage.setEmailInput(invalidEmail);
		registrationPage.setPasswordInput(invalidPassword);
		registrationPage.clickSubmitButton();

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

	@DisplayName("Check registration password does not contain digits and Latin letters")
	@Test
	public void testPasswordDoesNotContainDigitsAndLatinLetters() {
		String invalidPassword = "12345678";
		registrationPage.getRegistrationForm();
		registrationPage.setNameInput(testData.randomCyrillicName());
		registrationPage.setEmailInput("testmail.com");
		registrationPage.setPasswordInput(invalidPassword);
		registrationPage.clickSubmitButton();

		Assertions.assertEquals(
				RegistrationPage.ERROR_MESSAGE_PASSWORD_INVALID_CHARS,
				registrationPage.getPasswordErrorMessage());
	}
}
