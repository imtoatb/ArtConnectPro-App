package com.project.artconnect.service.impl;

import java.sql.Connection;
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

    @Override
    public List<Artist> getAllArtists() {
        try (Connection conn = ConnectionManager.getConnection()) {
            return artist_dao.findAll(conn);
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        List<Artist> allArtist = getAllArtists();
        return allArtist.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }
    
    @Override
    public Optional<Artist> getArtistById(Long id) {
        try (Connection conn = ConnectionManager.getConnection()) {
            return artist_dao.findById(conn, id);
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void createArtist(Artist artist) {
        try (Connection conn = ConnectionManager.getConnection()) {
            artist_dao.save(conn, artist);
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
    }

    @Override
    public void updateArtist(Artist artist) {
        try (Connection conn = ConnectionManager.getConnection()) {
            artist_dao.update(conn, artist);
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteArtist(String name) {
        try (Connection conn = ConnectionManager.getConnection()) {
            // D'abord trouver l'artiste pour avoir son ID
            Optional<Artist> artistOpt = getArtistByName(name);
            if (artistOpt.isPresent()) {
                Long artistId = artistOpt.get().getId();
                // Supprimer d'abord les œuvres (cascade)
                artwork_dao.deleteByArtistId(conn, artistId);
                // Puis supprimer l'artiste
                artist_dao.deleteById(conn, artistId);
            } else {
                System.err.println("[WARNING] Artist not found: " + name);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete failed: " + e.getMessage());
        }
    }
    
    @Override
    public void deleteArtistById(Long id) {
        try (Connection conn = ConnectionManager.getConnection()) {
            // Supprimer d'abord les œuvres (cascade)
            artwork_dao.deleteByArtistId(conn, id);
            // Puis supprimer l'artiste
            artist_dao.deleteById(conn, id);
            System.out.println("Artist and all their artworks deleted successfully");
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete by ID failed: " + e.getMessage());
        }
    }

    @Override
    public List<Discipline> getAllDisciplines() {
        try (Connection conn = ConnectionManager.getConnection()) {
            JdbcDisciplineDao disciplines = new JdbcDisciplineDao();
            return disciplines.getAllDisciplines(conn);
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Artist> searchArtists(String query, String disciplineName, String city) {
        List<Artist> allArtists = getAllArtists();
        
        return allArtists.stream()
                .filter(a -> query == null || query.isEmpty() || a.getName().toLowerCase().contains(query.toLowerCase()))
                .filter(a -> city == null || city.isEmpty() || a.getCity().equalsIgnoreCase(city))
                .filter(a -> disciplineName == null || disciplineName.isEmpty()
                        || a.getDisciplines().stream().anyMatch(d -> d.getName().equals(disciplineName)))
                .collect(Collectors.toList());
    }
}