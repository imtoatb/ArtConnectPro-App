package com.project.artconnect.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.persistence.JdbcArtistDao;
import com.project.artconnect.persistence.JdbcArtworkDao;
import com.project.artconnect.persistence.JdbcDisciplineDao;
import com.project.artconnect.service.ArtistService;
import com.project.artconnect.util.ConnectionManager;

public class ArtistServiceImpl implements ArtistService {
    private final JdbcArtistDao artist_dao = new JdbcArtistDao();
    private final JdbcArtworkDao artwork_dao = new JdbcArtworkDao();
    private Connection conn;
    private List<Artist> allArtists;

    public ArtistServiceImpl(){
        try {
            this.conn = ConnectionManager.getConnection();
        } catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
        refreshArtists();
    }

    @Override
    public List<Artist> getAllArtists(){
        return artist_dao.findAll(this.conn);
    }

    @Override
    public Optional<Artist> getArtistByName(String name){
        System.out.println("Got to getArtistByName: " + name);
        List<Artist> allArtist = getAllArtists();
        return allArtist.stream()
                .filter(a -> a.getName() != null && a.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Artist> getArtistById(Long id) {
        return artist_dao.findById(this.conn, id);
    }

    @Override
    public void createArtist(Artist artist){
        artist_dao.save(this.conn, artist);
        refreshArtists();
    }

    @Override
    public void updateArtist(Artist artist){
        artist_dao.update(this.conn, artist);
        refreshArtists();
    }

    @Override
    public void deleteArtist(String name){
        System.out.println("deleteArtist called with name: " + name);
        
        List<Artist> allArtists = getAllArtists();
        Optional<Artist> artistOpt = allArtists.stream()
                .filter(a -> a.getName() != null && a.getName().equals(name))
                .findFirst();
        
        if (artistOpt.isPresent()) {
            Artist artist = artistOpt.get();
            Long artistId = artist.getId();
            if (artistId != null) {
                deleteAllArtistData(artistId);
            }
            artist_dao.delete(this.conn, name);
            refreshArtists();
            System.out.println("Artist deleted successfully");
        } else {
            System.out.println("Artist not found: " + name);
        }
    }

    @Override
    public void deleteArtistById(Long id){
        System.out.println("deleteArtistById called with ID: " + id);
        deleteAllArtistData(id);
        artist_dao.deleteById(this.conn, id);
        refreshArtists();
        System.out.println("Artist deleted successfully by ID");
    }

    private void deleteAllArtistData(Long artistId) {
        try {
            String deleteBookingsSql = "DELETE b FROM Booking b JOIN Workshop w ON b.workshop_id = w.workshop_id WHERE w.artist_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteBookingsSql)) {
                ps.setLong(1, artistId);
                int deleted = ps.executeUpdate();
                System.out.println("Deleted " + deleted + " bookings for workshops of this artist");
            }
            
            String deleteWorkshopsSql = "DELETE FROM Workshop WHERE artist_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteWorkshopsSql)) {
                ps.setLong(1, artistId);
                int deleted = ps.executeUpdate();
                System.out.println("Deleted " + deleted + " workshops");
            }
            
            String deleteStyleSql = "DELETE FROM has_a_style WHERE artist_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteStyleSql)) {
                ps.setLong(1, artistId);
                int deleted = ps.executeUpdate();
                System.out.println("Deleted " + deleted + " style associations");
            }
            
            String selectArtworksSql = "SELECT artwork_id FROM Artwork WHERE artist_id = ?";
            List<Long> artworkIds = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(selectArtworksSql)) {
                ps.setLong(1, artistId);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        artworkIds.add(rs.getLong("artwork_id"));
                    }
                }
            }
            System.out.println("Found " + artworkIds.size() + " artworks");
            
            String deleteTagsSql = "DELETE FROM has_a_tag WHERE artwork_id = ?";
            for (Long artworkId : artworkIds) {
                try (PreparedStatement ps = conn.prepareStatement(deleteTagsSql)) {
                    ps.setLong(1, artworkId);
                    ps.executeUpdate();
                }
            }
            
            String deleteReviewsSql = "DELETE FROM Review WHERE artwork_id = ?";
            for (Long artworkId : artworkIds) {
                try (PreparedStatement ps = conn.prepareStatement(deleteReviewsSql)) {
                    ps.setLong(1, artworkId);
                    ps.executeUpdate();
                }
            }
            
            String updateArtworkExhibSql = "UPDATE Artwork SET exhib_id = NULL WHERE artist_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateArtworkExhibSql)) {
                ps.setLong(1, artistId);
                ps.executeUpdate();
            }
            
            String deleteArtworksSql = "DELETE FROM Artwork WHERE artist_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteArtworksSql)) {
                ps.setLong(1, artistId);
                int deleted = ps.executeUpdate();
                System.out.println("Deleted " + deleted + " artworks");
            }
            
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to delete artist data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Discipline> getAllDisciplines(){
        JdbcDisciplineDao disciplines = new JdbcDisciplineDao();
        return disciplines.getAllDisciplines(this.conn);
    }

    public List<Artist> searchArtists(String query, String disciplineName, String city){
        System.out.println("Got to searchArtists, query: " + query + ", discipline: " + disciplineName);
        this.allArtists = artist_dao.findAll(this.conn);
        if (this.allArtists == null) {
            return new ArrayList<>();
        }
        return this.allArtists.stream()
                .filter(a -> {
                    boolean matchesText = true;
                    if (query != null && !query.trim().isEmpty()) {
                        String lowerQ = query.toLowerCase();
                        boolean matchName = a.getName() != null && a.getName().toLowerCase().contains(lowerQ);
                        boolean matchCity = a.getCity() != null && a.getCity().toLowerCase().contains(lowerQ);
                        matchesText = matchName || matchCity;
                    }

                    boolean matchesDiscipline = true;
                    if (disciplineName != null && !disciplineName.trim().isEmpty()) {
                        matchesDiscipline = a.getDisciplines() != null && a.getDisciplines().stream()
                                .anyMatch(d -> d.getName() != null && d.getName().equalsIgnoreCase(disciplineName));
                    }

                    return matchesText && matchesDiscipline;
                })
                .collect(Collectors.toList());
    }

    // Rendre cette méthode PUBLIC
    public void refreshArtists() {
        this.allArtists = this.getAllArtists();
        //this.allArtists = artist_dao.findAll(this.conn);
        System.out.println("Refreshed artists list, count: " + (this.allArtists != null ? this.allArtists.size() : 0));
    }
}