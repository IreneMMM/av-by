package by.av.ui;

import by.av.ui.data.TestData;
import by.av.ui.page.SearchFilterPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;

public class SearchFilterPageTest extends BaseTest {
	SearchFilterPage searchFilterPage;
	TestData testData;

	@BeforeEach
	@Override
	public void setup(TestInfo testInfo) {
		super.setup(testInfo);
		searchFilterPage = new SearchFilterPage();
		testData = new TestData();
		searchFilterPage.open();
		acceptCookies();
	}

	@DisplayName("Check listing titles contain selected brand after filter")
	@Test
	public void testListingTitlesContainSelectedBrand() {
		String brand = testData.randomCarBrand();
		searchFilterPage.selectBrand(brand);
		searchFilterPage.clickShowListingsButton();
		searchFilterPage.waitForResultsLoaded(brand);

		List<String> listingTitles = searchFilterPage.getListingTitles();
		Assertions.assertFalse(listingTitles.isEmpty(), "No listings found after applying brand filter");

		for (String title : listingTitles) {
			Assertions.assertTrue(
					title.contains(brand),
					"Listing title does not contain brand '" + brand + "': " + title
			);
		}
	}
}
