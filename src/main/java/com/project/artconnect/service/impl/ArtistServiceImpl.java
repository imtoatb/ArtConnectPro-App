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
    private List<Artist> allArtists;

    public ArtistServiceImpl(){
        try{
            Connection conn = ConnectionManager.getConnection();
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("ArtistServiceImpl not instanciated");
        }
        this.allArtists = getAllArtists();


        /*
        try {
            if (conn.isClosed()) {
                System.out.println("service constructeur closed");
            }
        } catch (SQLException e) {
            System.out.println("Issue occurred with Connection: ");
            e.printStackTrace();
        }*/

    }

    public void initData(){
        System.out.println("Nothing");
    }

    @Override
    public List<Artist> getAllArtists(){
        try {
            if (conn.isClosed()) {
                System.out.println("service closed");
            }
        } catch (SQLException e) {
            System.out.println("Issue occurred with Connection: ");
            e.printStackTrace();
        }
        return artist_dao.findAll(this.conn);
    }

    @Override
    public Optional<Artist> getArtistByName(String name){
        System.out.println("Got to getArtistByName");
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
        System.out.println("Got to searchArtists");
        return this.allArtists.stream()
                .filter(a -> {
                    // If the search bar is empty, pass all.
                    if (query == null || query.trim().isEmpty()) return true;

                    String lowerQuery = query.toLowerCase();
                    boolean matchesName = a.getName() != null && a.getName().toLowerCase().contains(lowerQuery);
                    boolean matchesCity = a.getCity() != null && a.getCity().toLowerCase().contains(lowerQuery);

                    // Return true if the query matches EITHER the name OR the city
                    return matchesName || matchesCity;
                })
                .filter(a -> disciplineName == null || disciplineName.trim().isEmpty()
                        || a.getDisciplines().stream().anyMatch(d -> d.getName().equals(disciplineName)))
                .collect(Collectors.toList());

    }
}
