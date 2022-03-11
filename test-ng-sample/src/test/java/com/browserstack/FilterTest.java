package com.browserstack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class FilterTest extends BaseTest {

    @Test(dataProvider = "webdriver")
    public void filterVendorTest(WebDriver webDriver) throws Exception {
        webDriver.get("https://bstackdemo.com/");
        WebDriverWait wait = new WebDriverWait(webDriver, 25);
        webDriver.findElement(By.cssSelector("input[value='Apple'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "9 Product(s) found."));
        webDriver.findElement(By.cssSelector("input[value='Samsung'] + span")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".products-found"), "16 Product(s) found."));

        List<String> values = webDriver.findElements(By.cssSelector(".shelf-item__title"))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
       // List<String> expectedValues = CsvUtil.readSpecificColumn("src/test/resources/data/products.csv", 2);
      //  Assert..containsExactly(expectedValues.toArray(new String[0]));
    }
}