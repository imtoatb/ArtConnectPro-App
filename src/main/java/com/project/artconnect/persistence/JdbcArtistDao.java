package com.project.artconnect.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;

public class JdbcArtistDao implements ArtistDao {

    @Override
    public List<Artist> findAll(Connection conn) {
        String sql_statement = "SELECT * FROM Artist";
        List<Artist> artists = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    long curr_artist_id = rs.getLong("artist_id");
                    
                    // Récupérer les disciplines
                    List<Discipline> list_disciplines = getDisciplinesForArtist(conn, curr_artist_id);
                    
                    Artist curr_artist = new Artist(
                            rs.getString("name"),
                            rs.getString("bio"),
                            rs.getInt("birthYear"),
                            rs.getString("contactEmail"),
                            rs.getString("city"));
                    curr_artist.setId(rs.getLong("artist_id"));
                    curr_artist.setDisciplines(list_disciplines);
                    artists.add(curr_artist);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
        return artists;
    }
    
    private List<Discipline> getDisciplinesForArtist(Connection conn, long artistId) {
        List<Discipline> disciplines = new ArrayList<>();
        String sql = "SELECT d.name FROM Discipline d " +
                     "JOIN has_a_style h ON d.discipline_id = h.discipline_id " +
                     "WHERE h.artist_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    disciplines.add(new Discipline(rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Failed to get disciplines: " + e.getMessage());
        }
        return disciplines;
    }

    @Override
    public void save(Connection conn, Artist artist) {
        String sql = "INSERT INTO Artist (name, bio, birthYear, contactEmail, city) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.getName());
            ps.setString(2, artist.getBio());
            ps.setInt(3, artist.getBirthYear());
            ps.setString(4, artist.getContactEmail());
            ps.setString(5, artist.getCity());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Save failed: " + e.getMessage());
        }
    }

    @Override
    public void update(Connection conn, Artist artist) {
        String sql = "UPDATE Artist SET bio = ?, birthYear = ?, contactEmail = ?, city = ?, isActive = ?, phone = ?, website = ?, socialMedia = ? WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.getBio());
            ps.setInt(2, artist.getBirthYear());
            ps.setString(3, artist.getContactEmail());
            ps.setString(4, artist.getCity());
            ps.setBoolean(5, artist.isActive());
            ps.setString(6, artist.getPhone());
            ps.setString(7, artist.getWebsite());
            ps.setString(8, artist.getSocialMedia());
            ps.setString(9, artist.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Update failed: " + e.getMessage());
        }
    }

    @Override
    public void delete(Connection conn, String artistName) {
        String sql = "DELETE FROM Artist WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artistName);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Connection conn, Long id) {
        String sql = "DELETE FROM Artist WHERE artist_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete by ID failed: " + e.getMessage());
        }
    }

    @Override
    public List<Artist> findByCity(Connection conn, String city) {
        String sql = "SELECT * FROM Artist WHERE city = ?";
        List<Artist> artists = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artist artist = new Artist(
                            rs.getString("name"),
                            rs.getString("bio"),
                            rs.getInt("birthYear"),
                            rs.getString("contactEmail"),
                            rs.getString("city"));
                    artist.setId(rs.getLong("artist_id"));
                    artists.add(artist);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Find by city failed: " + e.getMessage());
        }
        return artists;
    }

    @Override
    public Optional<Artist> findById(Connection conn, Long id) {
        String sql = "SELECT * FROM Artist WHERE artist_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Artist artist = new Artist();
                    artist.setId(rs.getLong("artist_id"));
                    artist.setName(rs.getString("name"));
                    artist.setBio(rs.getString("bio"));
                    artist.setBirthYear(rs.getInt("birthYear"));
                    artist.setContactEmail(rs.getString("contactEmail"));
                    artist.setCity(rs.getString("city"));
                    artist.setPhone(rs.getString("phone"));
                    artist.setWebsite(rs.getString("website"));
                    artist.setSocialMedia(rs.getString("socialMedia"));
                    artist.setActive(rs.getBoolean("isActive"));
                    return Optional.of(artist);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Find by ID failed: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Artist> getAllActiveArtist(Connection conn) {
        String sql = "SELECT * FROM Artist WHERE isActive = TRUE";
        List<Artist> artists = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artist artist = new Artist(
                            rs.getString("name"),
                            rs.getString("bio"),
                            rs.getInt("birthYear"),
                            rs.getString("contactEmail"),
                            rs.getString("city"));
                    artist.setId(rs.getLong("artist_id"));
                    artists.add(artist);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Get active artists failed: " + e.getMessage());
        }
        return artists;
    }
}