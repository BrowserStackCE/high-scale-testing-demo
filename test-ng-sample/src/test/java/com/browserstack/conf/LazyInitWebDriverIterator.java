package com.browserstack.conf;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class LazyInitWebDriverIterator implements Iterator<Object[]> {
    private final String testMethodName;
    private final List<Platform> platforms;
    private final List<Object[]> testParams;
    private final boolean createManagedWebDriver;
    private int paramIdx = 0;

    public LazyInitWebDriverIterator(String testMethodName, Object[] testParams) {
        this.testMethodName = testMethodName;
        this.platforms = WebDriverFactory.getInstance().getPlatforms();
        this.createManagedWebDriver = false;
        if (testParams == null) {
            testParams = new Object[0];
        }

        List<Object[]> otherParamsList = new ArrayList<>();
        otherParamsList.add(testParams);
        this.testParams = this.populateTestParams(otherParamsList);
    }

    private List<Object[]> populateTestParams(List<Object[]> testParams) {
        int idx = 0;
        ArrayList tempTestParams = new ArrayList();

        do {
            Object[] testParam = testParams.get(idx);
            if (testParam == null) {
                testParam = new Object[0];
            }

            Iterator iterator = this.platforms.iterator();

            while(iterator.hasNext()) {
                Platform platform = (Platform)iterator.next();
                Object[] paramsWithPlatform = Arrays.copyOf(testParam, testParam.length + 1);
                paramsWithPlatform[paramsWithPlatform.length - 1] = platform;
                tempTestParams.add(paramsWithPlatform);
            }

            ++idx;
        } while(idx < testParams.size());

        return tempTestParams;
    }

    public boolean hasNext() {
        return this.paramIdx < this.testParams.size();
    }

    public Object[] next() {
        if (this.paramIdx >= this.testParams.size()) {
            throw new NoSuchElementException("No More Platforms configured to create WebDriver for.");
        } else {
            Object[] methodTestParams = (Object[])this.testParams.get(this.paramIdx++);
            if (methodTestParams[methodTestParams.length - 1] instanceof Platform) {
                Platform platform = (Platform)methodTestParams[methodTestParams.length - 1];
                if (this.createManagedWebDriver) {
                    ManagedWebDriver managedWebDriver = new ManagedWebDriver(this.testMethodName, platform);
                    methodTestParams[methodTestParams.length - 1] = managedWebDriver;
                } else {
                    WebDriver webDriver = WebDriverFactory.getInstance().createWebDriverForPlatform(platform, this.testMethodName);
                    methodTestParams[methodTestParams.length - 1] = webDriver;
                }
            }

            return methodTestParams;
        }
    }
}
