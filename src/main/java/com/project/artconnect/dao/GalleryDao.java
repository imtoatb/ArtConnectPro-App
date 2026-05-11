package com.project.artconnect.dao;

import com.project.artconnect.model.Gallery;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GalleryDao {
    Optional<Gallery> findById(Connection conn, Long id);

    Long findIdByTitle(Connection conn, String title);

    List<Gallery> findAll(Connection conn);
}
