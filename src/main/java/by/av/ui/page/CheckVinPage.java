package by.av.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckVinPage extends BasePage {
	private static final String CHECK_VIN_PAGE_URL = "https://av.by/vin";
	private static final String VIN_ERROR_MESSAGE = "VIN-номер состоит из 17 символов";

	private final By vinInput = By.xpath("//div[@class=\"vin-main__content\"]//input");
	private final By checkVinButton = By.xpath("//button[contains(text(),\"Проверить VIN\")]");
	private final By vinErrorMessage = By.xpath("//div[@class=\"error-message\"]");
	private final By whereFindVinButton = By.xpath("//button[contains(text(),\"Где найти VIN\")]");
	private final By exampleReportLink= By.xpath("//div[@class=\"vin-main__content\"]//a[contains(text(),\"Пример отчёта\")]");
	private final By whereFindVinTitle= By.xpath("//div[@class=\"modal__dialog modal__dialog--find-vin\"]//div[@class=\"modal__title\"]");
	private final By whereFindVinCloseButton= By.xpath("//div[div[text()=\"Где найти VIN\"]]//button[@class=\"modal__close\"]");

	public CheckVinPage() {
		super();
	}

	public void clickCheckVinButton() {
		driver.findElement(checkVinButton).click();
	}

	public void fillVinInput(String vinCode){
		WebElement vin = wait.until(ExpectedConditions.visibilityOfElementLocated(vinInput));
		vin.sendKeys(vinCode);
	}

	public void openExampleReport(){
		driver.findElement(exampleReportLink).click();
	}

	public void openImageWhereFindVin(){
		driver.findElement(whereFindVinButton).click();
	}

	public void closeImageWhereFindVin(){
		driver.findElement(whereFindVinCloseButton).click();
	}

	public String getTextVinErrorMessage(){
		return driver.findElement(vinErrorMessage).getText();
	}

	public String getTextWhereFindVinTitle(){
		return driver.findElement(whereFindVinTitle).getText();
	}

}
