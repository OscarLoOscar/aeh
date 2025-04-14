package com.example.dimerco.hawb.aehawb.infra;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Enviornment {

    public static String getAccessToken() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://apiportal.dimerco.com:8443/DimBotApi_Test/oauth2/token"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                        "  \"grant_type\": \"client_credentials\",\n" +
                        "  \"client_id\": \"rMhFLDBZsxdk0gSiUu7KY6DJwvMssJ4J\",\n" +
                        "  \"client_secret\": \"itoQUlk9PM5OGen4gu6QhT71v9pYzv7i\"\n" +
                        "}"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // Parse the JSON response to extract the access token
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            return jsonObject.get("access_token").getAsString();
        } else {
            throw new IOException("Failed to retrieve access token: " + response.body());
        }
    }

    public static void main(String[] args) {
        try {
            String accessToken = getAccessToken();
            System.out.println("Access Token: " + accessToken);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}