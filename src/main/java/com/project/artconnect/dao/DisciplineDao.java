package com.project.artconnect.dao;

import com.project.artconnect.model.Discipline;

import java.sql.Connection;
import java.util.List;

public interface DisciplineDao {
    List<Discipline> getAllDisciplines(Connection conn) ;
}
