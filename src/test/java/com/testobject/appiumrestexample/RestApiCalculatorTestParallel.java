package com.testobject.appiumrestexample;

import com.testobject.appiumrestexample.util.Parallelized;
import com.testobject.appiumrestexample.util.TestObject;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parallelized.class)
public class RestApiCalculatorTestParallel extends RestApiCalculatorTest {

    private static TestObject testObject = new TestObject("https://app.testobject.com:443/api/rest/devices/v1", TESTOBJECT_API_KEY);

    @Parameterized.Parameters
    public static List<Object[]> data() {
        List<String> devices = testObject.getAvailableAndroidDevices();
        Object[][] parameters = new Object[devices.size()][1];
        for (int i = 0; i <devices.size(); i++) {
            parameters[i][0] = devices.get(i);
        }

        return Arrays.asList(parameters);
    }

    public RestApiCalculatorTestParallel(String device){
        super(device);
    }
}