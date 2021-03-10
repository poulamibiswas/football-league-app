package com.example.demo.exceptions;

public class LeagueNotFoundException extends RuntimeException {
    public LeagueNotFoundException(String message) {
        super(message);
    }
}
