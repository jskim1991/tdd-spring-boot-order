package io.jay.tddspringbootorderinsideout.authentication.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
