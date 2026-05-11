package com.project.artconnect.dao;

import com.project.artconnect.model.CommunityMember;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface CommunityMemberDao {
    Optional<CommunityMember> findById(Connection conn, Long id);

    List<CommunityMember> findAll(Connection conn);
}
