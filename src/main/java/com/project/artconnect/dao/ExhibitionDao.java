package com.project.artconnect.dao;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.util.ConnectionManager;

import java.util.List;

public interface ExhibitionDao {
    List<Exhibition> findAll(ConnectionManager conn);

    void save(ConnectionManager conn, Exhibition exhibition);

    void update(ConnectionManager conn, Exhibition exhibition);

    void delete(ConnectionManager conn, String title);
}
