package by.av.ui;

import by.av.ui.driver.Driver;
import by.av.ui.data.TestData;
import by.av.ui.page.CheckVinPage;
import org.junit.jupiter.api.*;

public class CheckVinPageTest extends BaseTest {
	CheckVinPage checkVinPage;
	TestData testData;

	@BeforeEach
	@Override
	public void setup(TestInfo testInfo) {
		super.setup(testInfo);
		checkVinPage = new CheckVinPage();
		testData = new TestData();
		checkVinPage.openVinPage();
		acceptCookies();
	}

	@DisplayName("Check navigation to pre-report page with VIN in URL when valid VIN is entered")
	@Test
	public void testNavigateToPreReportPageWhenVinIsValid() {
		String vinValid = testData.randomVin();
		String reportPageUrl = "https://av.by/vin/prereport/";
		checkVinPage.fillVinInput(vinValid);
		checkVinPage.clickCheckVinButton();
		String currentUrl = checkVinPage.waitForPreReportPage();
		Assertions.assertTrue(currentUrl.contains(reportPageUrl), "URL is not correct");
		Assertions.assertTrue(currentUrl.contains(vinValid), "Vin code is incorrect");
	}

	@DisplayName("Check error message when VIN less 17 chars is entered")
	@Test
	public void testErrorMessageWhenVinIsInvalid() {
		String vinInvalid = testData.randomVin().substring(0, 16);
		String vinErrorMessageShortVin = "VIN-номер состоит из 17 символов";
		checkVinPage.fillVinInput(vinInvalid);
		checkVinPage.clickCheckVinButton();
		Assertions.assertEquals(vinErrorMessageShortVin, checkVinPage.getTextVinErrorMessage());
	}

	@DisplayName("Check error message when VIN is empty")
	@Test
	public void testErrorMessageWhenVinIsEmpty() {
		String vinErrorMessageEmptyVin = "VIN-номер состоит из 17 символов";
		checkVinPage.clickCheckVinButton();
		Assertions.assertEquals(vinErrorMessageEmptyVin, checkVinPage.getTextVinErrorMessage());
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
