package com.project.artconnect.service.impl;

import com.project.artconnect.model.Artwork;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.persistence.JdbcGalleryDao;
import com.project.artconnect.service.GalleryService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GalleryServiceImpl implements GalleryService {
    public Connection conn;
    private final JdbcGalleryDao galleryDao = new JdbcGalleryDao();

    public GalleryServiceImpl(){
        try{
            Connection conn = ConnectionManager.getConnection();
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("GalleryServiceImpl not instanciated");
        }
    }

    public List<Gallery> getAllGalleries(){
        return galleryDao.findAll(this.conn);
    }

    public Optional<Gallery> getGalleryByName(String name){
        List<Gallery> allGalleries = getAllGalleries();
        return allGalleries.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }

    public List<Exhibition> getExhibitionsByGallery(Gallery gallery){
        return gallery.getExhibitions(); // implémenter SQL pour mise à jour objet
    }
}
