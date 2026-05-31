package by.av.ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class HomePageTest extends BaseTest {

	@DisplayName("Check HomePage is opened")
	@Test
	public void checkHomePageIsOpened() {
		String expectedCopyrightText = "© 2001, ООО «Автоклассифайд», УНП 192787977, Минск, ул. Платонова, 20б, пом. 145";
		String actualCopyrightText = homePage.getCopyrightText();
		Assertions.assertEquals(expectedCopyrightText, actualCopyrightText);
	}

	@Test
	@DisplayName("Check switch to dark scheme")
	public void testSwitchToDarkTheme() {
		String selectedTheme = "dark";
		homePage.chooseTheme(selectedTheme);
		Assertions.assertTrue(homePage.isThemeActive(selectedTheme), "Dark theme is not selected");
	}

	@Test
	@DisplayName("Check switch to unknown scheme")
	public void testSwitchToUnknownTheme() {
		String unknownTheme = "blue";
		homePage.chooseTheme(unknownTheme);

		Assertions.assertFalse(homePage.isThemeActive(unknownTheme));
	}
}
