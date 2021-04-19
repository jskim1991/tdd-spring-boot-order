package io.jay.tddspringbootorderinsideout.user.store.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
