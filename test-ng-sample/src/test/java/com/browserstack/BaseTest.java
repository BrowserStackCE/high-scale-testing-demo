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

    /*@DataProvider(name="managedwebdriver", parallel = true)
    public static Object[] provideManagedWebDrivers(Method testMethod) {
        List<ManagedWebDriver> managedWebDrivers = createManagedWebDrivers(testMethod.getName());
        return managedWebDrivers.toArray(new ManagedWebDriver[0]);
    }

    protected static List<ManagedWebDriver> createManagedWebDrivers(String testMethodName) {
        WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
        final List<ManagedWebDriver> managedWebDrivers = new ArrayList<>();
        List<Platform> platforms = webDriverFactory.getPlatforms();
        platforms.forEach( p -> {
            managedWebDrivers.add(new ManagedWebDriver(testMethodName, p));
        });
        return managedWebDrivers;
    }}
*/
}