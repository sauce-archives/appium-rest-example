package com.testobject.appiumrestexample;

import com.testobject.appiumrestexample.screen.CalculatorScreen;
import io.appium.java_client.AppiumDriver;

public class Calculator {

    private final String device;
    private final AppiumDriver driver;

    public Calculator(String device, AppiumDriver driver) {
        this.device = device;
        this.driver = driver;
    }

    public CalculatorScreen calculatorScreen() { return new CalculatorScreen(driver, device); }

}