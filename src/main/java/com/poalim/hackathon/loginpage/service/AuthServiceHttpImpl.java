package com.poalim.hackathon.loginpage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceHttpImpl implements AuthService {

    private static final String API = "http://localhost:8080/authMe/v1/authenticate-user";

    @Override
    public boolean isValidUserAndOtp(String username, String otp) {

        try {
            HttpClient client = HttpClient.newHttpClient();
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", username);
            requestBody.put("otp", otp);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API))
                    .header("Content-Type", "application/json")
                    .header("userId", username)
                    .header("otp", otp)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);
            boolean isAuthenticated = jsonNode.get("otpAuthenticate").asBoolean();
            System.out.println("Response from authMeServer: " + isAuthenticated);
            return isAuthenticated;
        } catch (Exception e) {
            System.err.println("Error against validation api " + e);
            return false;
        }
    }
}
