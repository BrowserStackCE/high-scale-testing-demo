package com.browserstack.tests;


import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.logging.Level;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.browserstack.webdriver.testng.LazyInitWebDriverIterator;
import com.browserstack.webdriver.testng.listeners.WebDriverListener;

@Listeners({WebDriverListener.class})
public class BaseTest {

    @BeforeSuite
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
    }

    @DataProvider(name = "webdriver", parallel = true)
    public static Iterator<Object[]> provideWebDrivers(Method testMethod) {
        return new LazyInitWebDriverIterator(true, testMethod.getName(), new Object[0]);
    }

}
