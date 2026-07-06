package by.av.ui.service;

import by.av.ui.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CurrencyRateProvider extends BasePage {
	private static final String CURRENCY_PAGE_URL = "https://av.by/currency";
	private final By bynInput = By.xpath("//div[contains(text(), 'BYN')]/following-sibling::input");

	@Step("Get USD to BYN rate from currency page")
	public double getUsdToBynRate() {
		driver.get(CURRENCY_PAGE_URL);
		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(bynInput));
		String rateValue = input.getAttribute("value");
		return parseDecimal(rateValue);
	}

	private double parseDecimal(String value) {
		return Double.parseDouble(value.replace(',', '.'));
	}
}
