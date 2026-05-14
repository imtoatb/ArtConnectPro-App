package com.project.artconnect.service.impl;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.persistence.JdbcExhibitionDao;
import com.project.artconnect.persistence.JdbcGalleryDao;
import com.project.artconnect.service.GalleryService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GalleryServiceImpl implements GalleryService {
    private Connection conn;
    private final JdbcGalleryDao galleryDao = new JdbcGalleryDao();
    private final JdbcExhibitionDao exhibitionDao = new JdbcExhibitionDao();

    public GalleryServiceImpl(){
        try {
            this.conn = ConnectionManager.getConnection();
        } catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
    }

    @Override
    public List<Gallery> getAllGalleries(){
        List<Gallery> galleries = galleryDao.findAll(this.conn);
        List<Exhibition> allExhibitions = exhibitionDao.findAll(this.conn);
        for (Gallery g : galleries) {
            List<Exhibition> galleryExhibitions = allExhibitions.stream()
                    .filter(e -> e.getGallery() != null && e.getGallery().getName() != null && e.getGallery().getName().equals(g.getName()))
                    .collect(Collectors.toList());
            g.setExhibitions(galleryExhibitions);
        }
        return galleries;
    }

    @Override
    public Optional<Gallery> getGalleryByName(String name){
        List<Gallery> allGalleries = getAllGalleries();
        return allGalleries.stream()
                .filter(a -> a.getName() != null && a.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Exhibition> getExhibitionsByGallery(Gallery gallery){
        if (gallery == null) return new ArrayList<>();
        return exhibitionDao.findAll(this.conn).stream()
                .filter(e -> e.getGallery() != null && e.getGallery().getName() != null && e.getGallery().getName().equals(gallery.getName()))
                .collect(Collectors.toList());
    }

    public void refreshGalleries() {
        System.out.println("Refreshing galleries cache");
    }
}