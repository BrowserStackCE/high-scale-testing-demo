package com.browserstack.conf;

import org.openqa.selenium.WebDriver;

public class ManagedWebDriver {
    private final WebDriverFactory webDriverFactory;
    private String testName;
    private final Platform platform;
    private WebDriver webDriver;

    public ManagedWebDriver(String testMethodName, Platform platform) {
        this.testName = testMethodName;
        this.platform = platform;
        this.webDriverFactory = WebDriverFactory.getInstance();
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            this.webDriver = this.webDriverFactory.createWebDriverForPlatform(this.platform, this.testName);
        }

        return this.webDriver;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public void quitDriver() {
        if (this.webDriver != null) {
            this.webDriver.quit();
            this.webDriver = null;
        }

    }
}
