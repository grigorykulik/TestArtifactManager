package com.example.application.backend.slack;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class SlackUtils {
    private static String slackWebhookUrl = "https://hooks.slack.com/services/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";

    public static void sendMessage(String message) {
        System.out.println(message);

        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost("https://hooks.slack.com/services/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");
            StringEntity params =new StringEntity("{\"channel\":\"general\",\"text\":\""
                    +message+"\"} ");
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept","application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);

            // handle response here...
        }catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
