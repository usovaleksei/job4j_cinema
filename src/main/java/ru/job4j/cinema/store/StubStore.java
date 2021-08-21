package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StubStore implements Store {

    private static final StubStore INST = new StubStore();

    private static final AtomicInteger TICKET_ID = new AtomicInteger(4);
    private static final AtomicInteger USER_ID = new AtomicInteger(4);

    private final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, Film> films = new ConcurrentHashMap<>();

    private StubStore() {
    }

    public static StubStore getInstance() {
        return INST;
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        ticket.setId(TICKET_ID.incrementAndGet());
        this.tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Collection<Ticket> findAllTickets(int sessionId) {
        return this.tickets.values();
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(USER_ID.incrementAndGet());
        }
        this.users.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return this.users.get(email);
    }

    @Override
    public User findUserByPhone(String phone) {
        return this.users.get(phone);
    }

    @Override
    public Collection<Film> findAllFilms() {
        return this.films.values();
    }

    @Override
    public Film findFilmById(int id) {
        return this.films.get(id);
    }
}
