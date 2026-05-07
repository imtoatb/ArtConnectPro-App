package com.project.artconnect.dao;

import com.project.artconnect.model.Artwork;
import com.project.artconnect.util.ConnectionManager;

import java.util.List;

public interface ArtworkDao {
    List<Artwork> findAll(ConnectionManager conn);

    void save(ConnectionManager conn, Artwork artwork);

    void update(ConnectionManager conn, Artwork artwork);

    void delete(ConnectionManager conn, String title);

    List<Artwork> findByArtistName(ConnectionManager conn, String artistName);
}
