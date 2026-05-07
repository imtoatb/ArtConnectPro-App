package com.project.artconnect.dao;

import com.project.artconnect.model.Gallery;
import com.project.artconnect.util.ConnectionManager;

import java.util.List;
import java.util.Optional;

public interface GalleryDao {
    Optional<Gallery> findById(ConnectionManager conn, Long id);

    List<Gallery> findAll(ConnectionManager conn);
}
