package com.project.artconnect.dao;

import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.util.ConnectionManager;

import java.util.List;
import java.util.Optional;

public interface CommunityMemberDao {
    Optional<CommunityMember> findById(ConnectionManager conn, Long id);

    List<CommunityMember> findAll(ConnectionManager conn);
}
