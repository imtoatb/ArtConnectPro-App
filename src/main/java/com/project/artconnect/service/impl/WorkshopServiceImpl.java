package com.project.artconnect.service.impl;

import com.project.artconnect.model.Booking;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.persistence.JdbcWorkshopDao;
import com.project.artconnect.service.WorkshopService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WorkshopServiceImpl implements WorkshopService {
    public Connection conn;
    private final JdbcWorkshopDao workshopDao = new JdbcWorkshopDao();

    public WorkshopServiceImpl(){
        try( Connection conn = ConnectionManager.getConnection()){
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("WorkshopServiceImpl not instanciated");
        }
    }


    public List<Workshop> getAllWorkshops(){
        return workshopDao.findAll(this.conn);
    }

    public Optional<Workshop> getWorkshopByTitle(String title){
        List<Workshop> allWorkshops = getAllWorkshops();
        return allWorkshops.stream()
                .filter(a -> a.getTitle().equals(title))
                .findFirst();
    }

    public void bookWorkshop(Workshop workshop, CommunityMember member){
        Booking book = new Booking(workshop, member); // encore implémentation sql ...
    }

    public List<Booking> getBookingsByMember(CommunityMember member){
        return member.getBookings(); // implémenter SQL maybe aussi
    }

}
