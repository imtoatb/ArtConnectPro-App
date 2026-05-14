package com.project.artconnect.service.impl;

import com.project.artconnect.model.Booking;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.persistence.JdbcWorkshopDao;
import com.project.artconnect.service.WorkshopService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WorkshopServiceImpl implements WorkshopService {
    private Connection conn;
    private final JdbcWorkshopDao workshopDao = new JdbcWorkshopDao();

    public WorkshopServiceImpl(){
        try {
            this.conn = ConnectionManager.getConnection();
        } catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
        }
    }

    @Override
    public List<Workshop> getAllWorkshops(){
        return workshopDao.findAll(this.conn);
    }

    @Override
    public Optional<Workshop> getWorkshopByTitle(String title){
        List<Workshop> allWorkshops = getAllWorkshops();
        return allWorkshops.stream()
                .filter(a -> a.getTitle() != null && a.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public void bookWorkshop(Workshop workshop, CommunityMember member){
        Booking book = new Booking(workshop, member);
    }

    @Override
    public List<Booking> getBookingsByMember(CommunityMember member){
        return member.getBookings();
    }

    public void refreshWorkshops() {
        System.out.println("Refreshing workshops cache");
    }
}