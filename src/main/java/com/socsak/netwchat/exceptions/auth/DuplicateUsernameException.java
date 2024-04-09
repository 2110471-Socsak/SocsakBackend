package com.socsak.netwchat.exceptions.auth;

public class DuplicateUsernameException extends Exception{

    public DuplicateUsernameException() {
        super("Duplicate Username");
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
    public DuplicateUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
