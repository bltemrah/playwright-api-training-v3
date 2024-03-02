package com.qa.api.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class C_3_PostSingleUser {

    Playwright playwright;
    APIRequest request;
    APIRequestContext requestContext;

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        request = playwright.request();
        requestContext = request.newContext();
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

    public static String getRandomEmail() {
        String email = "test" + System.currentTimeMillis() + "@gmail.com";
        return email;
    }

    @Test
    public void postUserApiTest() throws IOException {

        Map<String, Object> data = new HashMap<>();
        data.put("name", "Test User");
        data.put("gender", "female");
        data.put("email", getRandomEmail());
        data.put("status", "active");

        APIResponse response = requestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer 2b6539b6e1f74991ac436f9ca5f381daa8417c13a48a1b2c747c64d8459930bc")
                        .setData(data)
        );

        int statusCode = response.status();
        System.out.println("Status Code : " + statusCode);

        String statusCodeText = response.statusText();
        System.out.println("Status Code Text : " + statusCodeText);
        Assert.assertEquals(statusCodeText, "Created");

        String url = response.url();
        System.out.println("URL : " + url);

    }
}
