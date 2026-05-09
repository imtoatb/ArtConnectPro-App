package com.project.artconnect.persistence;

import com.project.artconnect.dao.CommunityMemberDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.CommunityMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCommunityMemberDao implements CommunityMemberDao{

    @Override
    public List<CommunityMember> findAll(Connection conn){
        String sql_statement = "SELECT * FROM CommunityMember";        // initiate SQL query
        List<CommunityMember> members = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    members.add(new CommunityMember(
                            rs.getString("name"),
                            rs.getString("email")
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
        return members;
    }

    @Override
    public Optional<CommunityMember> findById(Connection conn, Long id){
        String sql_statement = "SELECT * FROM CommunityMember WHERE member_id = ?";

        CommunityMember myMember = new CommunityMember();

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    myMember.setName(rs.getString("name"));
                    myMember.setEmail(rs.getString("email"));
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

        return Optional.of(myMember);
    }
}
