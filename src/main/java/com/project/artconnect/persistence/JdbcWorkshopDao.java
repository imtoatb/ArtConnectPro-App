package com.project.artconnect.persistence;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.model.Workshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcWorkshopDao implements WorkshopDao {
    public Optional<Workshop> findById(Connection conn, Long id){
        String sql_statement = "SELECT * FROM Workshop WHERE member_id = ?";

        Workshop myWorkshop = new Workshop();

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    myWorkshop.setTitle(rs.getString("title"));
                    myWorkshop.setDescription(rs.getString("description"));
                    myWorkshop.setDate(rs.getDate("date").toLocalDate().atTime(LocalTime.now()));   // IDK WHAT TIME TO PUT I JUST PUT IT HERE IDK IF THERE S A WAY TO RETRIEVE IT
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

        return Optional.of(myWorkshop);
    }

    public List<Workshop> findAll(Connection conn){
        String sql_statement = "SELECT * FROM Workshop";        // initiate SQL query
        List<Workshop> workshops = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query

                JdbcArtistDao artistdao = new JdbcArtistDao();



                while(rs.next()) {
                    Long artist_id = rs.getLong("artist_id");
                    Optional<Artist> optional_artist = artistdao.findById(conn, artist_id);
                    Artist found_artist = null;
                    if (optional_artist.isPresent()) {
                        found_artist = optional_artist.get();
                    }
                    // for each row of the executed query (so the final table given as output)
                    Workshop newWorkshop = new Workshop(
                            rs.getString("title"),
                            rs.getDate("w_date").toLocalDate().atTime(LocalTime.now()), // IDK IF THERE S A WAY TO RETRIEVE IT THE CORRECT TIME
                            found_artist,
                            rs.getDouble("price"));
                    newWorkshop.setLevel(rs.getString("level"));
                    workshops.add(newWorkshop);

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
        return workshops;
    }
}
