package by.av.ui.service;

import by.av.ui.page.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CurrencyRateProvider extends BasePage {
	private static final Logger log = LogManager.getLogger(CurrencyRateProvider.class);
	private static final String CURRENCY_PAGE_URL = "https://av.by/currency";
	private final By bynInput = By.name("byn");
	private final By usdInput = By.name("usd");

	@Step("Get converted BYN for USD {amountUsd} from currency page")
	public double getConvertedBynForUsd(int amountUsd) {
		driver.get(CURRENCY_PAGE_URL);
		log.info("Opened currency page: {}", CURRENCY_PAGE_URL);
		
		WebElement usdField = wait.until(ExpectedConditions.visibilityOfElementLocated(usdInput));
		WebElement bynField = driver.findElement(bynInput);

		usdField.sendKeys(Keys.CONTROL + "a");
		usdField.sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.attributeToBe(bynField, "value", "0"));
		
		usdField.sendKeys(String.valueOf(amountUsd));
		log.info("Input USD amount: {}", amountUsd);

		if (amountUsd != 0) {
			wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBe(bynField, "value", "0")));
		}

		String rateValue = bynField.getAttribute("value");
		String cleanValue = rateValue.replaceAll("[^\\d,]", "").replace(',', '.');
		double resultByn = Double.parseDouble(cleanValue);
		
		log.info("Converted BYN value: {} (parsed as: {})", rateValue, resultByn);
		return resultByn;
	}
}
