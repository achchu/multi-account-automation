package perkbox.login.tests.stepdefs;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Class to setup the base for the step definition implementations
 */
public abstract class BaseSteps {
    protected static ChromeDriver chromeDriver;
    private Map<String, String> configs = new HashMap<>();

    public BaseSteps() throws IOException {
        this.readPropertyValue();
    }

    /**
     * Method to launch the browser with the desired configurations
     */
    protected static void openBrowser() {
        System.setProperty("webdriver.chrome.driver",
                "browser-drivers/chromedriver");

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--allow-running-insecure-content");
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        chromeOptions.addArguments("--proxy-server='direct://'");
        chromeOptions.addArguments("--proxy-bypass-list=*");
        chromeOptions.addArguments("--start-maximized");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("acceptSslCerts", true);
        capabilities.setCapability("acceptInsecureCerts", true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        chromeDriver = new ChromeDriver(capabilities);
    }

    /**
     * Method to explicitly wait before loading of a page/element
     *
     * @param timeMilliSeconds Time to wait in milli seconds
     */
    public void explicitWait(int timeMilliSeconds) {
        try {
            Thread.sleep(timeMilliSeconds);
        } catch (InterruptedException ex) {
            System.out.println("Sleep interrupted while waiting");
        }
    }

    /**
     * Method to get the property value from the configurations key/value map
     *
     * @param key Configuration key for which the value is needed
     * @return Value corresponding to the configuration key
     */
    public String getPropertyValue(String key) {
        return this.configs.get(key);
    }

    /**
     * Method to read the property value from the configuration file
     *
     * @throws IOException Exception to be thrown if unable to read from the file
     */
    private void readPropertyValue() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        for (String key : prop.stringPropertyNames()) {
            configs.put(key, prop.getProperty(key));
        }
    }
}
