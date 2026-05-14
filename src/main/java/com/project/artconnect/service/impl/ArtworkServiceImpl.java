package com.project.artconnect.service.impl;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;
import com.project.artconnect.persistence.JdbcArtworkDao;
import com.project.artconnect.service.ArtworkService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ArtworkServiceImpl implements ArtworkService {
    private Connection conn;
    private final JdbcArtworkDao artworkDao = new JdbcArtworkDao();

    public ArtworkServiceImpl(){
        try {
            this.conn = ConnectionManager.getConnection();
        } catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
    }
    
    @Override
    public List<Artwork> getAllArtworks(){
        return artworkDao.findAll(this.conn);
    }

    @Override
    public Optional<Artwork> getArtworkByTitle(String title){
        List<Artwork> allArtwork = getAllArtworks();
        return allArtwork.stream()
                .filter(a -> a.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public List<Artwork> getArtworksByArtist(Artist artist){
        return artworkDao.findByArtistName(this.conn, artist.getName());
    }

    @Override
    public void createArtwork(Artwork artwork){
        artworkDao.save(this.conn, artwork);
    }

    @Override
    public void updateArtwork(Artwork artwork){
        artworkDao.update(this.conn, artwork);
    }

    @Override
    public void deleteArtwork(String title){
        artworkDao.delete(this.conn, title);
    }

    public void refreshArtworks() {
        System.out.println("Refreshing artworks cache");
    }
}