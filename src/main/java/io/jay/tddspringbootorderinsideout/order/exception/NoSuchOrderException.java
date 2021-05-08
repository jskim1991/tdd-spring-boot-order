package io.jay.tddspringbootorderinsideout.order.exception;

public class NoSuchOrderException extends RuntimeException {

    public NoSuchOrderException(String message) {
        super(message);
    }
}
