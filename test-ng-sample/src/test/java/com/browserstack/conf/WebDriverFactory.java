package com.browserstack.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebDriverFactory {

    protected static final String CAPABILITIES_FILE_PROP = "capabilities.config";
    private static final String DEFAULT_CAPABILITIES_FILE = "capabilities.yml";

    private static volatile WebDriverFactory instance;
    private final String defaultBuildSuffix;
    private final Configuration configuration;

    private WebDriverFactory() {
        this.defaultBuildSuffix = String.valueOf(System.currentTimeMillis());
        this.configuration = parseConfig();
        List<Platform> platforms = configuration.getActivePlatforms();
    }

    public static WebDriverFactory getInstance() {
        if (instance == null) {
            synchronized (WebDriverFactory.class) {
                if (instance == null) {
                    instance = new WebDriverFactory();
                }
            }
        }
        return instance;
    }

    private Configuration parseConfig() {
        String capabilitiesConfigFile = System.getProperty(CAPABILITIES_FILE_PROP, DEFAULT_CAPABILITIES_FILE);
        URL resourceURL = WebDriverFactory.class.getClassLoader().getResource(capabilitiesConfigFile);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Configuration configuration;
        try {
            configuration = objectMapper.readValue(resourceURL, Configuration.class);
        } catch (IOException ioe) {
            throw new Error("Unable to parse capabilities file " + capabilitiesConfigFile, ioe);
        }
        return configuration;
    }

    public WebDriver createWebDriverForPlatform(Platform platform, String testName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserstack.user", configuration.getUser());
        caps.setCapability("browserstack.key", configuration.getAccessKey());
        caps.setCapability("os", platform.getOs());
        caps.setCapability("os_version", platform.getOsVersion());
        caps.setCapability("project", configuration.getProject());
        caps.setCapability("build", configuration.getProject() + defaultBuildSuffix);
        caps.setCapability("name", testName);
        if (platform.getDevice() == null) {
            caps.setCapability("browser", platform.getBrowser());
            caps.setCapability("browser_version", platform.getBrowserVersion());
        } else {
            caps.setCapability("device", platform.getDevice());
            caps.setCapability("real_mobile", "true");
        }
        try {
            System.out.println("Platform{}" + caps);
            return new RemoteWebDriver(new URL("https://hub-cloud.browserstack.com/wd/hub"), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException("WebDriver Initialisation Failure.");
        }
    }

    public List<Platform> getPlatforms() {
       return configuration.getPlatforms();
    }
}
