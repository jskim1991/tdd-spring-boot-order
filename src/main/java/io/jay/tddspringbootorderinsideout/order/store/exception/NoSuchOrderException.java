package io.jay.tddspringbootorderinsideout.order.store.exception;

public class NoSuchOrderException extends RuntimeException {

    public NoSuchOrderException(String message) {
        super(message);
    }
}
