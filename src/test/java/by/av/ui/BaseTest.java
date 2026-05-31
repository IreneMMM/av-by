package by.av.ui;

import by.av.ui.driver.Driver;
import by.av.ui.page.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeEach
    public void setup() {
        driver = Driver.getDriver();
        homePage = new HomePage();
        homePage.open();
        homePage.acceptCookies();
    }

    @AfterEach
    public void tearDown() {
        Driver.quitDriver();
    }
}
