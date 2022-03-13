package com.browserstack.tests;


import java.lang.reflect.Method;
import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.browserstack.webdriver.testng.LazyInitWebDriverIterator;
import com.browserstack.webdriver.testng.listeners.WebDriverListener;

@Listeners({WebDriverListener.class})
public class BaseTest {

    @DataProvider(name = "webdriver", parallel = true)
    public static Iterator<Object[]> provideWebDrivers(Method testMethod) {
        return new LazyInitWebDriverIterator(true, testMethod.getName(), new Object[0]);
    }

}
