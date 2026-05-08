package com.project.artconnect.dao;

import com.project.artconnect.model.Workshop;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface WorkshopDao {
    Optional<Workshop> findById(Connection conn, Long id);

    List<Workshop> findAll(Connection conn);
}
