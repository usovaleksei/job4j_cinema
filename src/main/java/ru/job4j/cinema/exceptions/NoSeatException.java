package ru.job4j.cinema.exceptions;

public class NoSeatException extends RuntimeException {
    public NoSeatException(String message) {
        super(message);
    }
}
