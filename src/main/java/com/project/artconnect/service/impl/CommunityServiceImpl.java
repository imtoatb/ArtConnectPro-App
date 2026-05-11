package com.project.artconnect.service.impl;

import com.project.artconnect.model.Artwork;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Review;
import com.project.artconnect.persistence.JdbcCommunityMemberDao;
import com.project.artconnect.service.CommunityService;
import com.project.artconnect.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CommunityServiceImpl implements CommunityService {
    public Connection conn;
    private final JdbcCommunityMemberDao communityMemberDao = new JdbcCommunityMemberDao();

    public CommunityServiceImpl(){
        try( Connection conn = ConnectionManager.getConnection()){
            this.conn = conn;
        }
        catch(SQLException e){
            System.err.println("[ERROR] Connection failed: " + e.getMessage());
            System.err.println("CommunityServiceImpl not instanciated");
        }
    }

    public List<CommunityMember> getAllMembers(){
        return communityMemberDao.findAll(this.conn);
    }

    public Optional<CommunityMember> getMemberByName(String name){
        List<CommunityMember> allMembers = getAllMembers();
        return allMembers.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }

    public List<Review> getReviewsByMember(CommunityMember member){
        return member.getReviews(); // pas d'implémentation des reviews venant de la BDD vers member pour l'instant
    }
}
