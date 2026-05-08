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
            // Get connection
            conn = ConnectionManager.getConnection();
            JdbcArtworkDao artworkDao = new JdbcArtworkDao();
            
            // TEST 1: findAll
            System.out.println("=== TEST 1: findAll ===");
            List<Artwork> artworks = artworkDao.findAll(conn);
            System.out.println("Nombre d'oeuvres trouvées: " + artworks.size());
            for (Artwork artwork : artworks) {
                System.out.println("  - " + artwork.getTitle() + " par " + 
                    (artwork.getArtist() != null ? artwork.getArtist().getName() : "Artiste inconnu"));
            }
            
            // TEST 2: findByArtistName
            System.out.println("\n=== TEST 2: findByArtistName (Degas) ===");
            List<Artwork> degasWorks = artworkDao.findByArtistName(conn, "Degas");
            for (Artwork artwork : degasWorks) {
                System.out.println("  - " + artwork.getTitle());
            }
            
            // TEST 3: Save a new artwork
            System.out.println("\n=== TEST 3: Save ===");
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
            System.out.println("✓ Oeuvre sauvegardée avec succès!");
            
            // TEST 4: Verify save worked
            System.out.println("\n=== TEST 4: Vérification de la sauvegarde ===");
            List<Artwork> allArtworks = artworkDao.findAll(conn);
            System.out.println("Total après sauvegarde: " + allArtworks.size());
            
            // TEST 5: Update
            System.out.println("\n=== TEST 5: Update ===");
            newArtwork.setPrice(55000.0);
            newArtwork.setDescription("Description modifiée");
            artworkDao.update(conn, newArtwork);
            System.out.println("✓ Oeuvre mise à jour avec succès!");
            
            // TEST 6: Delete
            System.out.println("\n=== TEST 6: Delete ===");
            artworkDao.delete(conn, "Test Oeuvre Monet");
            System.out.println("✓ Oeuvre supprimée avec succès!");
            
            // Final verification
            List<Artwork> finalList = artworkDao.findAll(conn);
            System.out.println("\n=== FINAL ===");
            System.out.println("Total final des oeuvres: " + finalList.size());
            
        } catch (SQLException e) {
            System.err.println("Erreur base de données: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("\nConnexion fermée.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}