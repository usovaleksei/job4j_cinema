package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.logging.Filter;

public class PsqlMain {

    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        /*Ticket ticket = new Ticket(1, 2, 2, 9);
        Ticket ticketOne = new Ticket(1, 2, 3, 4);
        User user = new User("Alex", "ya", "+79107777700");
        User userOne = new User("Sergey", "mail", "+79268888888");
        store.saveUser(user);
        store.saveUser(userOne);
        store.saveTicket(ticket);
        store.saveTicket(ticketOne);*/
        Collection<Ticket> ticketList = store.findAllTickets(3);
        for (Ticket ticket1 : ticketList) {
            System.out.println(ticket1);
        }
       /* System.out.println(store.findUserByEmail("mail"));
        System.out.println(store.findUserByPhone("+79107777700"));
        Collection<Film> filmsList = store.findAllFilms();
        for (Film film : filmsList) {
            System.out.println(film);
        }
        System.out.println("Поиск фильма по id: " + store.findFilmById(2));*/
    }
}
