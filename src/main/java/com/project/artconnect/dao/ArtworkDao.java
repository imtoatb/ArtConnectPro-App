package com.project.artconnect.dao;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ArtworkDao {
    List<Artwork> findAll(Connection conn);

    void save(Connection conn, Artwork artwork);

    void update(Connection conn, Artwork artwork);

    void delete(Connection conn, String title);

    void deleteById(Connection conn, Long id);

    void deleteByArtistId(Connection conn, Long artistId); 
    
    List<Artwork> findByArtistName(Connection conn, String artistName);

    Optional<Artist> findById(Connection conn, Long id);
}
