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
    private List<Artist> allArtists;

    public ArtistServiceImpl(){
        try{
            Connection conn = ConnectionManager.getConnection();
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            return new ArrayList<>();
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
        System.out.println(artist_dao.findAll(this.conn));
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
                    // 1. Check the Text Bar (Does the query match their Name OR City?)
                    boolean matchesText = true;
                    if (query != null && !query.trim().isEmpty()) {
                        String lowerQ = query.toLowerCase();
                        boolean matchName = a.getName() != null && a.getName().toLowerCase().contains(lowerQ);
                        boolean matchCity = a.getCity() != null && a.getCity().toLowerCase().contains(lowerQ);

                        // Pass this stage if EITHER name or city matches the text
                        matchesText = matchName || matchCity;
                    }

                    // 2. Check the Dropdown (Does the discipline match?)
                    boolean matchesDiscipline = true;
                    if (disciplineName != null && !disciplineName.trim().isEmpty()) {
                        matchesDiscipline = a.getDisciplines() != null && a.getDisciplines().stream()
                                .anyMatch(d -> d.getName().equalsIgnoreCase(disciplineName));
                    }

                    // 3. The artist must pass BOTH the text check AND the discipline check
                    return matchesText && matchesDiscipline;
                })
                .collect(Collectors.toList());

    }
}