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

    public Set<String> getDeviceIds(long suiteId) {
        return client
                .path("suites").path(Long.toString(suiteId))
                .path("deviceIds")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Set<String>>(Set.class));
    }

    public void setTestResult(boolean testResult, String sessionId) {
        client
                .path("session")
                .path(sessionId)
                .path("test").type(MediaType.APPLICATION_JSON_TYPE)
                .put(Collections.singletonMap("passed", testResult));
    }

//    public SuiteReport startSuiteTest(long suiteId, String className, String methodName, String deviceId) {
//
//        JSONObject jo = new JSONObject();
//        try {
//            jo.put("className", className);
//            jo.put("methodName", methodName);
//            jo.put("deviceId", deviceId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JSONArray ja = new JSONArray();
//        ja.put(jo);
//
//        return client
//                .path("suites").path(Long.toString(suiteId))
//                .path("reports")
//                .path("start")
//                .type(MediaType.APPLICATION_JSON_TYPE)
//                .post(ja);
//    }

    public SuiteReport startSuiteReport(long suiteId, Set<Test> tests) {
        return client
                .path("suites").path(Long.toString(suiteId))
                .path("reports")
                .path("start")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(SuiteReport.class, tests);
    }

    public SuiteReport finishSuiteReport(long suiteId, SuiteReport.Id suiteReportId) {
        return client
                .path("suites").path(Long.toString(suiteId))
                .path("reports").path(Long.toString(suiteReportId.value()))
                .path("finish")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .put(SuiteReport.class);
    }

    public TestReport finishTestReport(long suiteId, SuiteReport.Id suiteReportId, TestReport.Id testReportId, TestResult testResult) {
        return client
                .path("suites").path(Long.toString(suiteId))
                .path("reports").path(Long.toString(suiteReportId.value()))
                .path("results").path(Integer.toString(testReportId.value()))
                .path("finish")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .put(TestReport.class, testResult);
    }

    public void updateTestReportStatus(SessionId sessionId, boolean passed) {
        client
                .path("session")
                .path(sessionId.toString())
                .path("test").type(MediaType.APPLICATION_JSON_TYPE)
                .put(Collections.singletonMap("passed", passed));
    }

    public void updateTestReportName(SessionId sessionId, String suiteName, String testName) {
        Map<String, String> values = new HashMap<String, String>();
        values.put("suiteName", suiteName);
        values.put("testName", testName);

        client
                .path("session")
                .path(sessionId.toString())
                .path("test")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .put(Collections.singletonMap("passed", values));

    }

    public Set<String> getAvailableDevices() {
        return client
                .path("devices")
                .path("available").type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Set<String>>(Set.class));
    }

    public ArrayList<String> getAvailableAndroidDevices() {

        Set<String> availableDevices = client
                .path("devices")
                .path("available").type(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Set<String>>(Set.class));

        ArrayList<String> availableAndroidDevices = new ArrayList<String>();

        for (String device : availableDevices) {
            if (!device.contains("iPhone") && !device.contains("iPad")) {
                availableAndroidDevices.add(device);
            }
        }

        return availableAndroidDevices;

    }

}
