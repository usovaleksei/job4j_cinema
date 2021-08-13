package ru.job4j.cinema.servlet;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        User user = PsqlStore.instOf().findUserByEmail(req.getParameter("email"));
        if (user == null) {
            user = PsqlStore.instOf().findUserByPhone(req.getParameter("phone"));
        }
        if (user == null) {
            PsqlStore.instOf().saveUser(
                    new User(req.getParameter("username"),
                            req.getParameter("email"),
                            req.getParameter("phone")));
            user = PsqlStore.instOf().findUserByEmail(req.getParameter("email"));
        }
        Ticket ticket = new Ticket(
                Integer.parseInt(req.getParameter("sessionId")),
                Integer.parseInt(req.getParameter("row")),
                Integer.parseInt(req.getParameter("cell")),
                user.getId());
        if (PsqlStore.instOf().saveTicket(ticket).getId() != 0) {
            resp.getWriter().write("Вы забронировали ряд " + ticket.getRow() + " место " + ticket.getCell());
        } else {
            resp.getWriter().write("Выбранные места уже заняты");
        }
    }
}
