package com.sber.practika.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnectToUrl {
    public static boolean authentication (String method, JSONObject json) throws IOException {
        try {
            URI url = new URI("http://localhost:8080/authentication/" + method);
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(url)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(json)))
                    .header("Content-type", "application/json")
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject answer = new JSONObject(response.body());
            System.out.println(answer + "\n");
            return answer.get("status").equals("ok");

        } catch (JSONException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean transfer(String jwt, String method, JSONObject json) throws IOException {
        try {
            URI url = new URI("http://localhost:8080/api/" + method);
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(url).POST(HttpRequest.BodyPublishers.ofString(String.valueOf(json)))
                    .header("Content-type", "application/json")
                    .header("Authorization", "Bearer " + jwt)
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject answer = new JSONObject(response.body());
            System.out.println(answer + "\n");
            return answer.get("status").equals("ok");

        } catch (JSONException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String getJWT() {
        try {
            URI url = new URI("http://localhost:8080/authentication/phone");
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(url)
                    .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(new JSONObject()
                                                .put("phone", "85068407596")
                                                .put("password", "95a81899484bec3b5a087bf9649996eba4856a5c673358b0a0e2e93715662dfc"))))
                    .header("Content-type", "application/json")
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject answer = new JSONObject(response.body());
            return answer.get("token").toString();

        } catch (JSONException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
