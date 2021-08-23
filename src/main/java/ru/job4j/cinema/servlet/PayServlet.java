package ru.job4j.cinema.servlet;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.PsqlStore;
import ru.job4j.cinema.store.Store;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class PayServlet extends HttpServlet {

    private final Store store = PsqlStore.instOf();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        String phone = req.getParameter("phone");
        String username = req.getParameter("username");
        int sessionId = Integer.parseInt(req.getParameter("sessionId"));
        int row = Integer.parseInt(req.getParameter("row"));
        int cell = Integer.parseInt(req.getParameter("cell"));

        int userId = Optional
                .ofNullable(this.store.findUserByPhone(phone))
                .orElse(this.store.saveUser(new User(username, phone)))
                .getId();

        this.store.saveTicket(new Ticket(sessionId, row, cell, userId));
    }
}
