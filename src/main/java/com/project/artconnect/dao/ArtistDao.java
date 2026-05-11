package com.project.artconnect.dao;

import com.project.artconnect.model.Artist;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for Artist entity.
 */
public interface ArtistDao {
    List<Artist> findAll(Connection conn);

    void save(Connection conn, Artist artist);

    void update(Connection conn, Artist artist);

    void delete(Connection conn, String artistName);

    List<Artist> findByCity(Connection conn, String city);

    Optional<Artist> findById(Connection conn, Long id);
}
