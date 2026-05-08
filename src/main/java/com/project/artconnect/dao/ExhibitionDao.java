package com.project.artconnect.dao;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;

public interface ExhibitionDao {
    List<Exhibition> findAll(Connection conn);

    void save(Connection conn, Exhibition exhibition);

    void update(Connection conn, Exhibition exhibition);

    void delete(Connection conn, String title);
}
