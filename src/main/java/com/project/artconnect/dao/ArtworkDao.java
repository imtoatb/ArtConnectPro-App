package com.project.artconnect.dao;

import com.project.artconnect.model.Artwork;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;

public interface ArtworkDao {
    List<Artwork> findAll(Connection conn);

    void save(Connection conn, Artwork artwork);

    void update(Connection conn, Artwork artwork);

    void delete(Connection conn, String title);

    List<Artwork> findByArtistName(Connection conn, String artistName);
}
