package com.browserstack;

import com.browserstack.conf.LazyInitWebDriverIterator;
import com.browserstack.conf.WebDriverListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.util.Iterator;

@Listeners({WebDriverListener.class})
public class BaseTest {

    @DataProvider(name = "webdriver", parallel = true)
    public static Iterator<Object[]> provideWebDrivers(Method testMethod) {
        return new LazyInitWebDriverIterator(testMethod.getName(),
                new Object[0]);
    }

}