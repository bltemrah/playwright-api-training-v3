package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;

public class C_2_GetSingleUser {

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


    @Test
    public void getUserApiTest() throws IOException {

        APIResponse response = requestContext.get("https://gorest.co.in/public/v2/users",
        RequestOptions.create()
                .setQueryParam("gender", "female")
                .setQueryParam("status", "active"));


        int statusCode = response.status();
        System.out.println("Status Code : " + statusCode);

        String statusCodeText = response.statusText();
        System.out.println("Status Code Text : " + statusCodeText);
        Assert.assertEquals(statusCodeText, "OK");

        Assert.assertEquals(response.ok(), true);

        String url = response.url();
        System.out.println("URL : " + url);

//        Assert.assertEquals(url, "https://gorest.co.in/public/v2/users");

        // Headers
        Map<String, String> headers = response.headers();
//        System.out.println("Headers : " + headers);

        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8");

        String responseBodyText = response.text();
        System.out.println("Response Body Text : " + responseBodyText);

        String responseBody = response.body().toString();
        System.out.println("Response Body : " + responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        String jsonResponseAsString = jsonResponse.toPrettyString();
        System.out.println("Json Response : " + jsonResponseAsString);

    }
}
