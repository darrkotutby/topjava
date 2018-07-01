package ru.javawebinar.topjava.repository.exception;

public class NotExistsException extends RuntimeException {
    public NotExistsException(String message) {
        super(message);
    }
}
