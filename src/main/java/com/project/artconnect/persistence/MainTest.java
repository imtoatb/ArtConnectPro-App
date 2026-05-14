package com.project.artconnect.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;
import com.project.artconnect.util.ConnectionManager;

public class MainTest {
    public static void main(String[] args) {
        Connection conn = null;
        
        try {
            conn = ConnectionManager.getConnection();
            JdbcArtistDao artistDao = new JdbcArtistDao();
            
            System.out.println("test 1: getAllActiveArtist");
            List<Artist> artists = artistDao.getAllActiveArtist(conn);
            System.out.println("found: " + artists.size());
            for (Artist artist : artists) {
                System.out.println("  - " + artist.getName() + " bio : " + artist.getBio());
            }
            /*
            System.out.println("\ntest 2: findByArtistName (Degas)");
            List<Artwork> degasWorks = artworkDao.findByArtistName(conn, "Degas");
            for (Artwork artwork : degasWorks) {
                System.out.println("  - " + artwork.getTitle());
            }
            
            System.out.println("\ntest 3: save");
            Artist testArtist = new Artist();
            testArtist.setName("Claude Monet");
            
            Artwork newArtwork = new Artwork();
            newArtwork.setTitle("Test Oeuvre Monet");
            newArtwork.setCreationYear(1890);
            newArtwork.setType("Painting");
            newArtwork.setMedium("Oil on canvas");
            newArtwork.setDimensions("92x73 cm");
            newArtwork.setDescription("Test description");
            newArtwork.setPrice(50000.0);
            newArtwork.setStatus(Artwork.Status.FOR_SALE);
            newArtwork.setArtist(testArtist);
            
            artworkDao.save(conn, newArtwork);
            System.out.println("saved");
            
            System.out.println("\ntest 4: verify save");
            List<Artwork> allArtworks = artworkDao.findAll(conn);
            System.out.println("total after save: " + allArtworks.size());
            
            System.out.println("\ntest 5: update");
            newArtwork.setPrice(55000.0);
            newArtwork.setDescription("updated description");
            artworkDao.update(conn, newArtwork);
            System.out.println("updated");
            
            System.out.println("\ntest 6: delete");
            artworkDao.delete(conn, "Test Oeuvre Monet");
            System.out.println("deleted");
            
            List<Artwork> finalList = artworkDao.findAll(conn);
            System.out.println("\ntotal final: " + finalList.size());
        */
        } catch (SQLException e) {
            System.err.println("database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("\nconnection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}