package ru.neoflex.excaption;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }

}