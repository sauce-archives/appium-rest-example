package com.testobject.appiumrestexample;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testobject.appium.common.data.SuiteReport;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class RestApiCalculatorTest {

    private AppiumDriver driver;
    private TestObject testObject;

    public static final String TESTOBJECT_API_KEY = "7CDE94EFFE3E4EF4A773DB2728688C53";

    /* This is the setup that will be run before the test. */
    @Before
    public void setUp() throws Exception {

        testObject = new TestObject("https://app.testobject.com:443/api/rest/devices/v1", TESTOBJECT_API_KEY);

        ArrayList<String> availableDevices = testObject.getAvailableAndroidDevices();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
        capabilities.setCapability("testobject_app_id", "1");

        int count = 0;

        // Run on available devices sequentially
        for (String device : availableDevices) {

            if (count < 10) {

                capabilities.setCapability("testobject_device", device);
                driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);
                count++;

            }

        }

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void watcherTest() {

        /* Get the elements. */
        MobileElement buttonTwo = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/digit2")));
        MobileElement buttonPlus = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/plus")));
        MobileElement buttonEquals = (MobileElement)(driver.findElement(By.id("net.ludeke.calculator:id/equal")));
        MobileElement resultField = (MobileElement)(driver.findElement(By.xpath("//android.widget.EditText[1]")));

        /* Add two and two. */
        buttonTwo.click();
        buttonPlus.click();
        buttonTwo.click();
        buttonEquals.click();

        /* Check if within given time the correct result appears in the designated field. */

        new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElement(resultField, "4"));

    }

}