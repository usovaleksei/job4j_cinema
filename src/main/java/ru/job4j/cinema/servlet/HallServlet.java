package ru.job4j.cinema.servlet;

import org.json.JSONArray;
import ru.job4j.cinema.store.PsqlStore;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HallServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        int sessionId = Integer.parseInt(req.getParameter("sessionId"));
        var tickets = PsqlStore.instOf().findAllTickets(sessionId);
        resp.getWriter().println(new JSONArray(tickets));
    }
}
