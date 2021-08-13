package ru.job4j.cinema.model;

public class Film {

    private int sessionId;
    private String name;

    public Film(int sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Film{"
                + "name='" + name + '\''
                + '}';
    }
}
