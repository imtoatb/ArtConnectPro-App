package com.project.artconnect.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.artconnect.dao.ArtworkDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;

/**
 * JDBC implementation for ArtworkDao.
 */
public class JdbcArtworkDao implements ArtworkDao {

    @Override
    public List<Artwork> findAll(Connection conn) {
        String sql = "SELECT a.*, art.name as artist_name " +
                     "FROM Artwork a " +
                     "LEFT JOIN Artist art ON a.artist_id = art.artist_id";
        List<Artwork> artworks = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setTitle(rs.getString("title"));
                    
                    int creationYear = rs.getInt("creationYear");
                    if (!rs.wasNull()) {
                        artwork.setCreationYear(creationYear);
                    }
                    
                    artwork.setType(rs.getString("type"));
                    artwork.setMedium(rs.getString("medium"));
                    artwork.setDimensions(rs.getString("dimensions"));
                    artwork.setDescription(rs.getString("description"));
                    artwork.setPrice(rs.getDouble("price"));
                    
                    // Handle status conversion
                    String statusStr = rs.getString("status");
                    if (statusStr != null) {
                        switch (statusStr) {
                            case "FOR SALE":
                                artwork.setStatus(Artwork.Status.FOR_SALE);
                                break;
                            case "SOLD":
                                artwork.setStatus(Artwork.Status.SOLD);
                                break;
                            case "EXHIBITED":
                                artwork.setStatus(Artwork.Status.EXHIBITED);
                                break;
                            default:
                                artwork.setStatus(Artwork.Status.FOR_SALE);
                        }
                    }
                    
                    // Set Artist
                    String artistName = rs.getString("artist_name");
                    if (artistName != null) {
                        Artist artist = new Artist();
                        artist.setName(artistName);
                        artwork.setArtist(artist);
                    }
                    
                    artworks.add(artwork);
                }
            } catch (Error e) {
                System.out.println("Something went wrong with the query execution");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
        return artworks;
    }

    @Override
    public void save(Connection conn, Artwork artwork) {
        String sql = "INSERT INTO Artwork (title, creationYear, type, medium, dimensions, " +
                     "description, price, status, artist_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, (SELECT artist_id FROM Artist WHERE name = ?))";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artwork.getTitle());
            ps.setObject(2, artwork.getCreationYear());
            ps.setString(3, artwork.getType());
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getDimensions());
            ps.setString(6, artwork.getDescription());
            ps.setDouble(7, artwork.getPrice());
            
            // Convert status to database format
            String statusStr = null;
            if (artwork.getStatus() != null) {
                switch (artwork.getStatus()) {
                    case FOR_SALE:
                        statusStr = "FOR SALE";
                        break;
                    case SOLD:
                        statusStr = "SOLD";
                        break;
                    case EXHIBITED:
                        statusStr = "EXHIBITED";
                        break;
                }
            }
            ps.setString(8, statusStr);
            ps.setString(9, artwork.getArtist() != null ? artwork.getArtist().getName() : null);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void update(Connection conn, Artwork artwork) {
        String sql = "UPDATE Artwork SET " +
                     "title = ?, creationYear = ?, type = ?, medium = ?, dimensions = ?, " +
                     "description = ?, price = ?, status = ?, " +
                     "artist_id = (SELECT artist_id FROM Artist WHERE name = ?) " +
                     "WHERE title = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artwork.getTitle());
            ps.setObject(2, artwork.getCreationYear());
            ps.setString(3, artwork.getType());
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getDimensions());
            ps.setString(6, artwork.getDescription());
            ps.setDouble(7, artwork.getPrice());
            
            // Convert status to database format
            String statusStr = null;
            if (artwork.getStatus() != null) {
                switch (artwork.getStatus()) {
                    case FOR_SALE:
                        statusStr = "FOR SALE";
                        break;
                    case SOLD:
                        statusStr = "SOLD";
                        break;
                    case EXHIBITED:
                        statusStr = "EXHIBITED";
                        break;
                }
            }
            ps.setString(8, statusStr);
            ps.setString(9, artwork.getArtist() != null ? artwork.getArtist().getName() : null);
            ps.setString(10, artwork.getTitle()); // WHERE clause uses the original title
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void delete(Connection conn, String title) {
        String sql = "DELETE FROM Artwork WHERE title = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public Optional<Artist> findById(Connection conn, Long id) {
        String sql = "SELECT a.*, art.name as artist_name " +
                    "FROM Artwork a " +
                    "LEFT JOIN Artist art ON a.artist_id = art.artist_id " +
                    "WHERE a.artwork_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Artist artist = new Artist();
                    artist.setName(rs.getString("artist_name"));
                    // Tu peux ajouter plus de champs si besoin
                    return Optional.of(artist);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Find by ID failed: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    @Override
    public List<Artwork> findByArtistName(Connection conn, String artistName) {
        String sql = "SELECT a.*, art.name as artist_name " +
                     "FROM Artwork a " +
                     "LEFT JOIN Artist art ON a.artist_id = art.artist_id " +
                     "WHERE art.name LIKE ?";
        List<Artwork> artworks = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + artistName + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setTitle(rs.getString("title"));
                    
                    int creationYear = rs.getInt("creationYear");
                    if (!rs.wasNull()) {
                        artwork.setCreationYear(creationYear);
                    }
                    
                    artwork.setType(rs.getString("type"));
                    artwork.setMedium(rs.getString("medium"));
                    artwork.setDimensions(rs.getString("dimensions"));
                    artwork.setDescription(rs.getString("description"));
                    artwork.setPrice(rs.getDouble("price"));
                    
                    // Handle status conversion
                    String statusStr = rs.getString("status");
                    if (statusStr != null) {
                        switch (statusStr) {
                            case "FOR SALE":
                                artwork.setStatus(Artwork.Status.FOR_SALE);
                                break;
                            case "SOLD":
                                artwork.setStatus(Artwork.Status.SOLD);
                                break;
                            case "EXHIBITED":
                                artwork.setStatus(Artwork.Status.EXHIBITED);
                                break;
                            default:
                                artwork.setStatus(Artwork.Status.FOR_SALE);
                        }
                    }
                    
                    // Set Artist
                    String name = rs.getString("artist_name");
                    if (name != null) {
                        Artist artist = new Artist();
                        artist.setName(name);
                        artwork.setArtist(artist);
                    }
                    
                    artworks.add(artwork);
                }
            } catch (Error e) {
                System.out.println("Something went wrong with the query execution");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
        return artworks;
    }

    @Override
    public void deleteByArtistId(Connection conn, Long artistId) {
        String sql = "DELETE FROM Artwork WHERE artist_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, artistId);
            int deletedCount = ps.executeUpdate();
            System.out.println("Deleted " + deletedCount + " artworks for artist ID: " + artistId);
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete artworks by artist failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Connection conn, Long id) {
        String sql = "DELETE FROM Artwork WHERE artwork_id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[ERROR] Delete by ID failed: " + e.getMessage());
        }
    }

}