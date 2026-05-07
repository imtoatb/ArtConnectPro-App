package com.project.artconnect.dao;

import com.project.artconnect.model.Artist;
import com.project.artconnect.util.ConnectionManager;

import java.util.List;

/**
 * Data Access Object for Artist entity.
 */
public interface ArtistDao {
    List<Artist> findAll(ConnectionManager conn);

    void save(ConnectionManager conn, Artist artist);

    void update(ConnectionManager conn, Artist artist);

    void delete(ConnectionManager conn, String artistName);

    List<Artist> findByCity(ConnectionManager conn, String city);
}
