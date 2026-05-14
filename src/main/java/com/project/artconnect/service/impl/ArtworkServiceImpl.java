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
    public Connection conn;
    private final JdbcArtworkDao artworkDao = new JdbcArtworkDao();

    public ArtworkServiceImpl(){
        try{
            Connection conn = ConnectionManager.getConnection();
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("ArtworkServiceImpl not instanciated");
        }

    }
    public List<Artwork> getAllArtworks(){
        return artworkDao.findAll(this.conn);
    }

    public Optional<Artwork> getArtworkByTitle(String title){
        List<Artwork> allArtwork = getAllArtworks();
        return allArtwork.stream()
                .filter(a -> a.getTitle().equals(title))
                .findFirst();
    }

    public List<Artwork> getArtworksByArtist(Artist artist){
        return artworkDao.findByArtistName(this.conn, artist.getName());
    }

    public void createArtwork(Artwork artwork){
        artworkDao.save(this.conn, artwork);
    }

    public void updateArtwork(Artwork artwork){
        artworkDao.update(this.conn, artwork);
    }

    public void deleteArtwork(String title){
        artworkDao.delete(this.conn, title);
    }
}
