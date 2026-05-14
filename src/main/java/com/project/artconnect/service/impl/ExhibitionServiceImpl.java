package com.project.artconnect.service.impl;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.persistence.JdbcExhibitionDao;
import com.project.artconnect.service.ExhibitionService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExhibitionServiceImpl implements ExhibitionService {
    private Connection conn;
    private final JdbcExhibitionDao exhibitionDao = new JdbcExhibitionDao();

    public ExhibitionServiceImpl() {
        try {
            this.conn = ConnectionManager.getConnection();
        } catch (SQLException e) {
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
    }

    @Override
    public List<Exhibition> getAllExhibitions() {
        return exhibitionDao.findAll(this.conn);
    }

    @Override
    public Optional<Exhibition> getExhibitionByTitle(String title) {
        return getAllExhibitions().stream()
                .filter(e -> e.getTitle() != null && e.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public void createExhibition(Exhibition exhibition) {
        exhibitionDao.save(this.conn, exhibition);
    }

    @Override
    public void updateExhibition(Exhibition exhibition) {
        exhibitionDao.update(this.conn, exhibition);
    }

    @Override
    public void deleteExhibition(String title) {
        exhibitionDao.delete(this.conn, title);
    }

    public void refreshExhibitions() {
        System.out.println("Refreshing exhibitions cache");
    }
}