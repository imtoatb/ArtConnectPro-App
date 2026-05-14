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

/**
 * JDBC implementation for ArtistDao.
 * TODO: Students must implement this using JDBC and SQL.
 */
public class JdbcArtistDao implements ArtistDao {

    @Override
    public List<Artist> findAll(Connection conn) {
        String sql_statement = "SELECT * FROM Artist";        // initiate SQL query
        List<Artist> artists = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    Artist artist = new Artist(
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getInt("birthYear"),
                        rs.getString("contactEmail"),
                        rs.getString("city")
                    );
                    artist.setId(rs.getLong("artist_id"));  // NOUVEAU : set l'ID
                    artist.setPhone(rs.getString("phone"));
                    artist.setWebsite(rs.getString("website"));
                    artist.setSocialMedia(rs.getString("socialMedia"));
                    artist.setActive(rs.getBoolean("isActive"));
                    artists.add(artist);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
        return artists;
    }

    @Override
    public void save(Connection conn, Artist artist) {
        String sql_statement = "INSERT INTO " +
                "Artist (name, bio, birthYear, contactEmail, city) " +
                "VALUES (?, ?, ?, ?, ?)";        // initiate SQL query

        // YES now there are comments, they were added after the 3 first methods, and I wanted to make it clear in the first one
        // hence I copied them too, it's easier for me
        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){      // prepare the query for the placeholders values

            ps.setString(1, artist.getName());
            ps.setString(2, artist.getBio());
            ps.setInt(3, artist.getBirthYear());
            ps.setString(4, artist.getContactEmail());
            ps.setString(5, artist.getCity());

            ps.executeUpdate();                // necessary if you want to perform an update

        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void update(Connection conn, Artist artist) {
        String sql_statement = "UPDATE Artist SET " +
                "bio = ?, birthYear = ?, contactEmail = ?, city = ?, isActive = ?, phone = ?, website = ?, socialMedia = ? " +
                "WHERE name = ?";        // I M ASSUMING WE HAVE TO NOT CHANGE THE OG STRUCTURE

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){      // prepare the query for the placeholders values

            ps.setString(1, artist.getBio());
            ps.setInt(2, artist.getBirthYear());
            ps.setString(3,artist.getContactEmail());
            ps.setString(4, artist.getCity());
            ps.setBoolean(5, artist.isActive());
            ps.setString(6, artist.getPhone());
            ps.setString(7, artist.getWebsite());
            ps.setString(8, artist.getSocialMedia());

            ps.setString(9, artist.getName());

            ps.executeUpdate();                // necessary if you want to perform an update

        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void delete(Connection conn, String artistName) {
        String sql_statement = "DELETE FROM Artist WHERE name = ?";        // initiate SQL query

        try (PreparedStatement ps = conn.prepareStatement(sql_statement);){      // prepare the query for the placeholders values

            ps.setString(1, artistName);

            ps.executeUpdate();                // necessary if you want to perform an update

        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void deleteById(Connection conn, Long id) {
        String sql = "DELETE FROM Artist WHERE artist_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete failed: " + e.getMessage());
        }
    }

    @Override
    public List<Artist> findByCity(Connection conn, String city) {
        String sql_statement = "SELECT * FROM artist WHERE city = ?";
        List<Artist> artists = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    artists.add(new Artist(
                            rs.getString("name"),
                            rs.getString("bio"),
                            rs.getInt("birthYear"),
                            rs.getString("contactEmail"),
                            rs.getString("city")
                    ));
                }
            } catch (Error e){                                          // handling errors just in case
                System.out.println("Something went wrong with the query execution");
                e.printStackTrace();
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
        return artists;
    }

    @Override
    public Optional<Artist> findById(Connection conn, Long id){
        String sql_statement = "SELECT * FROM Artist WHERE artist_id = ?";

        Artist myArtist = new Artist();

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    myArtist.setName(rs.getString("name"));
                    myArtist.setBio(rs.getString("bio"));
                    myArtist.setBirthYear(rs.getInt("birthYear"));
                    myArtist.setPhone(rs.getString("phone"));
                }
            } catch (Error e){                                          // handling errors just in case
                System.out.println("Something went wrong with the query execution");
                e.printStackTrace();
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: ");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }

        return Optional.of(myArtist);
    }
}

