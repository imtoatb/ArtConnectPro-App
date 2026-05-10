package com.project.artconnect.persistence;

import com.project.artconnect.dao.GalleryDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGalleryDao implements GalleryDao {

    @Override
    public Optional<Gallery> findById(Connection conn, Long id){
        String sql_statement = "SELECT * FROM Gallery WHERE gallery_id = ?";

        Gallery myGallery = new Gallery();

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    myGallery.setName(rs.getString("name"));
                    myGallery.setAddress(rs.getString("address"));
                    myGallery.setRating(rs.getDouble("rating"));
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

        return Optional.of(myGallery);
    }

    @Override
    public List<Gallery> findAll(Connection conn){
        String sql_statement = "SELECT * FROM Gallery";        // initiate SQL query
        List<Gallery> galleries = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    galleries.add(new Gallery(
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getDouble("rating")
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
        return galleries;
    }

    @Override
    public Long findIdByTitle(Connection conn, String title){
        String sql_statement = "SELECT * FROM Gallery WHERE title = ?";

        Long found_id = null;

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            ps.setString(1, title);

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    found_id = rs.getLong("gallery_id");
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

        return found_id;
    }

}
