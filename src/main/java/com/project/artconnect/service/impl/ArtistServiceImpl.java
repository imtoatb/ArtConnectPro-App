package com.project.artconnect.service.impl;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.persistence.JdbcArtistDao;
import com.project.artconnect.persistence.JdbcDisciplineDao;
import com.project.artconnect.service.ArtistService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ArtistServiceImpl implements ArtistService {
    public Connection conn;
    private final JdbcArtistDao artist_dao = new JdbcArtistDao();

    public ArtistServiceImpl(){
        try( Connection conn = ConnectionManager.getConnection()){
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("ArtistServiceImpl not instanciated");
        }

    }

    @Override
    public List<Artist> getAllArtists(){
        return artist_dao.findAll(this.conn);
    }

    @Override
    public Optional<Artist> getArtistByName(String name){
        List<Artist> allArtist = getAllArtists();
        return allArtist.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }

    public void createArtist(Artist artist){
        artist_dao.save(this.conn, artist);
    }

    public void updateArtist(Artist artist){
        artist_dao.update(this.conn, artist);
    }

    public void deleteArtist(String name){
        artist_dao.delete(this.conn, name);
    }

    public List<Discipline> getAllDisciplines(){
        JdbcDisciplineDao disciplines = new JdbcDisciplineDao();
        return disciplines.getAllDisciplines(this.conn);
    }

    public List<Artist> searchArtists(String query, String disciplineName, String city){
        Map<String, Artist> artists = new LinkedHashMap<>();
        List<Artist> allArtists = getAllArtists();
        for (Artist artist : allArtists){
            artists.put(artist.getName(), artist);
        }

        return artists.values().stream()
                .filter(a -> query == null || a.getName().toLowerCase().contains(query.toLowerCase()))
                .filter(a -> city == null || city.isEmpty() || a.getCity().equalsIgnoreCase(city))
                .filter(a -> disciplineName == null
                        || a.getDisciplines().stream().anyMatch(d -> d.getName().equals(disciplineName)))
                .collect(Collectors.toList());
    }
}
