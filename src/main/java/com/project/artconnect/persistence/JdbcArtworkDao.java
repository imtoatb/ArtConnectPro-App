package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtworkDao;
import com.project.artconnect.model.Artwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.project.artconnect.util.ConnectionManager;

/**
 * JDBC implementation for ArtworkDao.
 */
public class JdbcArtworkDao implements ArtworkDao {

    @Override
    public List<Artwork> findAll(ConnectionManager conn) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT a.*, ar.name as artist_name FROM Artwork a " +
                     "LEFT JOIN Artist ar ON a.artist_id = ar.artist_id";
        
        try (Connection connection = conn.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                artworks.add(mapResultSetToArtwork(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all artworks", e);
        }
        
        return artworks;
    }

    @Override
    public void save(ConnectionManager conn, Artwork artwork) {
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public void update(ConnectionManager conn, Artwork artwork) {
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public void delete(ConnectionManager conn, String title) {
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }

    @Override
    public List<Artwork> findByArtistName(ConnectionManager conn, String artistName) {
        throw new UnsupportedOperationException("JDBC Implementation not yet provided.");
    }
}




