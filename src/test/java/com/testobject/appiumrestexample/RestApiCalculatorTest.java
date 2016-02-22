package com.testobject.appiumrestexample;

import com.testobject.appiumrestexample.util.Parallelized;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

@RunWith(Parallelized.class)
public class RestApiCalculatorTest {

    private AppiumDriver driver;
    private String device;

    public static final String TESTOBJECT_API_KEY = "7CDE94EFFE3E4EF4A773DB2728688C53";

    protected RestApiCalculatorTest(String device) {
        this.device = device;
    }

    /* This is the setup that will be run before the test. */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
        capabilities.setCapability("testobject_app_id", "1");
        capabilities.setCapability("testobject_device", device);

        driver = new AndroidDriver(new URL("https://app.testobject.com:443/api/appium/wd/hub"), capabilities);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void twoPlusTwoTest() {

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