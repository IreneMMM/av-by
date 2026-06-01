package by.av.ui;

import by.av.ui.driver.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class HomePageTest extends BaseTest {

	@DisplayName("Check footer displays expected copyright text")
	@Test
	public void testCopyrightTextIsDisplayed() {
		Assertions.assertEquals(homePage.getExpectedCopyrightText(), homePage.getCopyrightText());
	}

	@DisplayName("Check theme can be selected")
	@ParameterizedTest
	@ValueSource(strings = {"dark", "light", "auto"})
	void testThemeCanBeSelected(String theme) {
		homePage.chooseTheme(theme);
		Assertions.assertTrue(homePage.isThemeActive(theme));
	}

	@Test
	@DisplayName("Check theme is saved after page refresh")
	public void testThemeIsSavedAfterPageRefresh() {
		String selectedTheme = "dark";
		homePage.chooseTheme(selectedTheme);
		homePage.refreshPage();
		Assertions.assertTrue(homePage.isThemeActive(selectedTheme), selectedTheme + " theme is not saved");
	}

	@Test
	@DisplayName("Check theme is changed from one to another")
	public void testThemeIsChanged() {
		String firstTheme = "dark";
		String secondTheme = "light";
		homePage.chooseTheme(firstTheme);
		Assertions.assertTrue(homePage.isThemeActive(firstTheme), firstTheme + "theme is not saved");
		homePage.chooseTheme(secondTheme);
		Assertions.assertTrue(homePage.isThemeActive(secondTheme), secondTheme + "theme is not saved");
	}

	@Test
	@DisplayName("Check navigation items are displayed")
	public void testNavigationItemsAreDisplayed() {
		for (String navItem : homePage.getNavigationItems()) {
			Assertions.assertTrue(homePage.isNavigationItemDisplayed(navItem), navItem + " is not displayed");
		}
	}

	@Test
	@DisplayName("Check vin check page is opened")
	public void testVinCheckPageIsOpened() {
		homePage.clickVinCheckNavLink();
		Assertions.assertEquals("https://av.by/vin", Driver.getDriver().getCurrentUrl());
	}

	@Test
	@DisplayName("Check login slider is opened if unauthorized user click 'Подать объявление'")
	public void testLoginSliderOpenedIfUnauthUser() {
		homePage.clickSubmitAdButton();
		Assertions.assertTrue(homePage.isLoginSliderOpened(), "Login slider was not opened!");
	}
}
