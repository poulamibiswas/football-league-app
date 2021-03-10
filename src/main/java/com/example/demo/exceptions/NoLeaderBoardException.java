package com.example.demo.exceptions;

public class NoLeaderBoardException extends RuntimeException {
    public NoLeaderBoardException(String message) {
        super(message);
    }
}
