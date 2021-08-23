package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.exceptions.NoSeatException;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db_cinema.properties"))
        ) {
            cfg.load(io);
        } catch (IOException e) {
            LOG.error("Error reading database settings", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            LOG.error("Error loading database driver", e);
        }
        this.pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        this.pool.setUrl(cfg.getProperty("jdbc.url"));
        this.pool.setUsername(cfg.getProperty("jdbc.username"));
        this.pool.setPassword(cfg.getProperty("jdbc.password"));
        this.pool.setMinIdle(5);
        this.pool.setMaxIdle(10);
        this.pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        String query =
                "insert into ticket(session_id, row, cell, account_id) values(?, ?, ?, ?)";
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getAccountId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error", e);
            throw new NoSeatException("Выбранные места заняты");
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> findAllTickets(int sessionId) {
        String query =
                "select * from ticket where session_id = (?)";
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ticketList.add(new Ticket(rs.getInt("id"),
                            rs.getInt("session_id"),
                            rs.getInt("row"),
                            rs.getInt("cell"),
                            rs.getInt("account_id")));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error");
        }
        return ticketList;
    }

    @Override
    public User saveUser(User user) {
        String query =
                "insert into account(username, phone) values(?, ?)";
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error", e);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        String query =
                "select id, username, email, phone from account where email = (?)";
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error", e);
        }
        return user;
    }

    @Override
    public User findUserByPhone(String phone) {
        User user = null;
        String query =
                "select id, username, phone from account where phone = (?)";
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error", e);
        }
        return user;
    }

    @Override
    public Collection<Film> findAllFilms() {
        List<Film> filmList = new ArrayList<>();
        String query = "select * from film";
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    filmList.add(new Film(rs.getInt("id"),
                            rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error", e);
        }
        return filmList;
    }

    @Override
    public Film findFilmById(int id) {
        Film film = null;
        String query =
                "select id, name from film where id = (?)";
        try (Connection cn = this.pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    film = new Film(rs.getInt("id"),
                            rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            LOG.error("Request execution error", e);
        }
        return film;
    }
}
