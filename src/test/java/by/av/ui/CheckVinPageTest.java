package by.av.ui;

import by.av.ui.driver.Driver;
import by.av.ui.page.CheckVinPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckVinPageTest extends BaseTest {

	@DisplayName("Check ")
	@Test
	public void test1() {
		homePage.clickVinCheckNavLink();
		CheckVinPage checkVinPage = new CheckVinPage();
		//обавить генирацию рандом винпаге
		checkVinPage.fillVinInput("00000000000000000");
		checkVinPage.clickCheckVinButton();
		String currentUrl = Driver.getDriver().getCurrentUrl();
		String expectedUrlPart = "https://av.by/vin/prereport/";
		Assertions.assertTrue(currentUrl.contains(expectedUrlPart),
				"Переход на страницу предотчета не произошел. Текущий URL: " + currentUrl);

	}


	@Test
	public void test() {
		homePage.clickVinCheckNavLink();
		CheckVinPage checkVinPage = new CheckVinPage();
		checkVinPage.fillVinInput("00000000000000000");
		checkVinPage.clickCheckVinButton();
		String expectedUrlPart = "https://av.by/vin/prereport/";
		WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
		boolean isUrlCorrect = wait.until(ExpectedConditions.urlContains(expectedUrlPart));
		Assertions.assertTrue(isUrlCorrect,
				"Переход на страницу предотчета не произошел за 10 секунд. Текущий URL: "
						+ Driver.getDriver().getCurrentUrl());
	}
}
