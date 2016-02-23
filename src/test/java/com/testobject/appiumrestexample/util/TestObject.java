package com.testobject.appiumrestexample.util;

import com.sun.jersey.api.client.GenericType;
import org.openqa.selenium.remote.SessionId;
import org.testobject.appium.common.data.SuiteReport;
import org.testobject.appium.common.data.TestReport;
import org.testobject.appium.common.data.TestResult;
import org.testobject.appium.junit.internal.Test;

import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by grago on 08/02/16.
 */
public class TestObject {

    private RestClient client;

    public TestObject(String apiEndpoint, String apiKey) {
        client = RestClient.Factory.createClient(apiEndpoint, apiKey);
    }

    public Set<String> getAvailableDevices() {
        return client
                .path("devices")
                .path("available").type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Set<String>>(Set.class));
    }

    public ArrayList<String> getAvailableAndroidDevices() {

        Set<String> availableDevices = getAvailableDevices();
        ArrayList<String> availableAndroidDevices = new ArrayList<String>();

        for (String device : availableDevices) {
            if (!device.contains("iPhone") && !device.contains("iPad")) {
                availableAndroidDevices.add(device);
            }
        }

        return availableAndroidDevices;

    }

    public ArrayList<String> getAvailableIOSDevices() {

        Set<String> availableDevices = getAvailableDevices();
        ArrayList<String> availableIOSDevices = new ArrayList<String>();

        for (String device : availableDevices) {
            if (device.contains("iPhone") || device.contains("iPad")) {
                availableIOSDevices.add(device);
            }
        }

        return availableIOSDevices;

    }

}
