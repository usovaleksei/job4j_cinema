package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.Collection;

public interface Store {

    Ticket saveTicket(Ticket ticket);

    Collection<Ticket> findAllTickets(int sessionId);

    User saveUser(User user);

    User findUserByPhone(String phone);

    Collection<Film> findAllFilms();

    Film findFilmById(int id);
}
