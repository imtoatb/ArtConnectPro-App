package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.util.ConnectionManager;

import java.util.List;

/**
 * JDBC implementation for ArtistDao.
 * TODO: Students must implement this using JDBC and SQL.
 */
public class JdbcArtistDao implements ArtistDao {

    @Override
    public List<Artist> findAll(ConnectionManager conn) {
        // TODO: Implement SELECT * FROM artist
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public void save(ConnectionManager conn, Artist artist) {
        // TODO: Implement INSERT INTO artist(...) VALUES(...)
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public void update(ConnectionManager conn, Artist artist) {
        // TODO: Implement UPDATE artist SET ... WHERE name = ?
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public void delete(ConnectionManager conn, String artistName) {
        // TODO: Implement DELETE FROM artist WHERE name = ?
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public List<Artist> findByCity(ConnectionManager conn, String city) {
        // TODO: Implement SELECT * FROM artist WHERE city = ?
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }
}
