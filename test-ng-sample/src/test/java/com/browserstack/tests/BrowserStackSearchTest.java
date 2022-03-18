package com.browserstack.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.browserstack.webdriver.testng.ManagedWebDriver;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class BrowserStackSearchTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void testGoogleSearchBrowserStack(ManagedWebDriver managedWebDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriver webDriver = managedWebDriver.getWebDriver();
        webDriver.get("https://www.google.com");
        WebDriverWait wait = new WebDriverWait(webDriver, 30);

        /* =================== Execute ================= */
        if (webDriver.findElements(By.cssSelector("div > button:nth-of-type(2)")).size() > 0 ) {
            webDriver.findElement(By.cssSelector("div > button:nth-of-type(2)")).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='q']")));
        webDriver.findElement(By.cssSelector("input[name='q']")).sendKeys("BrowserStack");
        webDriver.findElement(By.cssSelector("input[name='q']")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.id("center_col"))));
        webDriver.findElement(By.cssSelector("a[href*='BrowserStack']")).click();
//        wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.partialLinkText("Live"))));
//        String pageTitle = webDriver.getTitle();
//
        /* =================== Verify ================= */
//        Assert.assertTrue(StringUtils.isNoneEmpty(pageTitle));
//        Assert.assertTrue(pageTitle.contains("Most Reliable App & Cross Browser Testing Platform | BrowserStack"));
    }
}
