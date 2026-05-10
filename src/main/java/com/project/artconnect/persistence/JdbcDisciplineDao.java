package com.project.artconnect.persistence;

import com.project.artconnect.dao.DisciplineDao;
import com.project.artconnect.model.Discipline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcDisciplineDao implements DisciplineDao {
    public List<Discipline> getAllDisciplines(Connection conn){
        String sql_statement = "SELECT * FROM Discipline";    // initiate SQL query
        List<Discipline> disciplines = new ArrayList<>();             // initiate result

        try (PreparedStatement ps = conn.prepareStatement(sql_statement)){     // prepare the query for the placeholders values

            try (ResultSet rs = ps.executeQuery()){                     // safely execute the query
                while(rs.next()) {                                      // for each row of the executed query (so the final table given as output)
                    disciplines.add(new Discipline(rs.getString("name")));
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
        return disciplines;
    }
}
