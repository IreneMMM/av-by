package by.av.ui;

import by.av.ui.driver.Driver;
import by.av.ui.page.CheckVinPage;
import org.junit.jupiter.api.*;

public class CheckVinPageTest extends BaseTest {
	CheckVinPage checkVinPage;

	@BeforeEach
	@Override
	public void setup() {
		initDriver();
		checkVinPage = new CheckVinPage();
		checkVinPage.openVinPage();
		acceptCookies();
	}

	@DisplayName("Check navigation to pre-report page with VIN in URL when valid VIN is entered")
	@Test
	public void testNavigateToPreReportPageWhenVinIsValid() {
		String vinValid = "00000000000000000";
		checkVinPage.fillVinInput(vinValid);
		checkVinPage.clickCheckVinButton();
		String currentUrl = checkVinPage.waitForPreReportPage();
		Assertions.assertTrue(currentUrl.contains(checkVinPage.getExpectedUrlPart()), "URL is not correct");
		Assertions.assertTrue(currentUrl.contains(vinValid), "Vin code is incorrect");
	}

	@DisplayName("Check error message when VIN less 17 chars is entered")
	@Test
	public void testErrorMessageWhenVinIsInvalid() {
		String vinInvalid = "5654646346363563";
		checkVinPage.fillVinInput(vinInvalid);
		checkVinPage.clickCheckVinButton();
		String actualErrorMessage = checkVinPage.getTextVinErrorMessage();
		Assertions.assertEquals(checkVinPage.getVinErrorMessageShortVin(), actualErrorMessage);
	}

	@DisplayName("Check 'where find VIN image' is displayed")
	@Test
	public void testWhereFindVinImageIsDisplayed() {
		checkVinPage.openImageWhereFindVin();
		Assertions.assertEquals("Где найти VIN", checkVinPage.getTextWhereFindVinTitle());
	}

	@DisplayName("Check example report opens after click on VIN page")
	@Test
	void testExampleReportIsOpened() {
		checkVinPage.clickExampleReportLink();
		Assertions.assertEquals(checkVinPage.getExampleReportUrl(), Driver.getDriver().getCurrentUrl());
	}
}
