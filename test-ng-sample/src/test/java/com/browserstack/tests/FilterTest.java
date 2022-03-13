package com.browserstack.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.browserstack.webdriver.testng.ManagedWebDriver;

public class FilterTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void testVendorFilter(ManagedWebDriver managedWebDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriver webDriver = managedWebDriver.getWebDriver();

        webDriver.get("https://bstackdemo.com/");
        WebDriverWait wait = new WebDriverWait(webDriver, 30);

        /* =================== Execute & Verify ================= */
//        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Apple'] + span")));
        webDriver.findElement(By.cssSelector("input[value='Apple'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "9 Product(s) found."));
        Assert.assertEquals(webDriver.findElements(By.cssSelector(".shelf-item__title")).size(), 9, "Apple Filter displays incorrect results");

        webDriver.findElement(By.cssSelector("input[value='Apple'] + span")).click();
        webDriver.findElement(By.cssSelector("input[value='Samsung'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "7 Product(s) found."));
        Assert.assertEquals(webDriver.findElements(By.cssSelector(".shelf-item__title")).size(), 7, "Samsung Filter displays incorrect results");
    }
}
