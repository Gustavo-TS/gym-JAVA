package com.gym.dto;

public class auth_response {
    private String token;

    public auth_response(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
