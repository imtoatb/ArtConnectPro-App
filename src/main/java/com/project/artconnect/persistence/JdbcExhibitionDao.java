package com.project.artconnect.persistence;

import com.project.artconnect.dao.ExhibitionDao;
import com.project.artconnect.persistence.JdbcGalleryDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcExhibitionDao implements ExhibitionDao {

    @Override
    public List<Exhibition> findAll(Connection conn){
        String sql_statement = "SELECT * FROM Exhibition";        // initiate SQL query
        List<Exhibition> exhibitions = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {
                    // for each row of the executed query (so the final table given as output)
                    JdbcGalleryDao galleryDao = new JdbcGalleryDao();

                    Long gallery_id = rs.getLong("gallery_id");
                    Optional<Gallery> optional_gallery = galleryDao.findById(conn, gallery_id);
                    Gallery found_gallery = null;
                    if (optional_gallery.isPresent()) {
                        found_gallery = optional_gallery.get();
                    }

                    exhibitions.add(new Exhibition(
                            rs.getString("title"),
                            rs.getDate("startDate").toLocalDate(),
                            rs.getDate("endDate").toLocalDate(),
                            found_gallery
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
        return exhibitions;
    }

    @Override
    public void save(Connection conn, Exhibition exhibition){
        String sql_statement = "INSERT INTO " +
                "Exhibition (name, startDate, endDate, description, gallery_id) " +
                "VALUES (?, ?, ?, ?, ?)";        // initiate SQL query

        JdbcGalleryDao galleryDao = new JdbcGalleryDao();
        Long gallery_id = galleryDao.findIdByTitle(conn, exhibition.getTitle());

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){      // prepare the query for the placeholders values

            ps.setString(1, exhibition.getTitle());
            ps.setDate(2, Date.valueOf(exhibition.getStartDate()));
            ps.setDate(3, Date.valueOf(exhibition.getEndDate()));
            ps.setString(4, exhibition.getDescription());
            ps.setLong(5, gallery_id);

            ps.executeUpdate();                // necessary if you want to perform an update

        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void update(Connection conn, Exhibition exhibition){
        String sql_statement = "UPDATE Exhibition SET " +
                "title = ?, startDate = ?, endDate = ?, description = ?, gallery_id = ?, curator_name = ?, theme = ? " +
                "WHERE name = ?";

        JdbcGalleryDao galleryDao = new JdbcGalleryDao();
        Long gallery_id = galleryDao.findIdByTitle(conn, exhibition.getTitle());

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){      // prepare the query for the placeholders values

            ps.setString(1, exhibition.getTitle());
            ps.setDate(2, Date.valueOf(exhibition.getStartDate()));
            ps.setDate(3, Date.valueOf(exhibition.getEndDate()));
            ps.setString(4, exhibition.getDescription());
            ps.setLong(5, gallery_id);
            ps.setString(6, exhibition.getCuratorName());
            ps.setString(7, exhibition.getTheme());

            ps.setString(8, exhibition.getTitle());

            ps.executeUpdate();                // necessary if you want to perform an update

        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }

    @Override
    public void delete(Connection conn, String title){
        String sql_statement = "DELETE FROM Exhibition WHERE name = ?";        // initiate SQL query

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){      // prepare the query for the placeholders values

            ps.setString(1, title);

            ps.executeUpdate();                // necessary if you want to perform an update

        } catch (UnsupportedOperationException e) {
            System.out.println("Issue occurred with Connection: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("Verify the URL, username, and password in ConnectionManager.");
        }
    }
}
