package com.project.artconnect.service;

import java.util.List;
import java.util.Optional;

import com.project.artconnect.model.Exhibition;

public interface ExhibitionService {
    List<Exhibition> getAllExhibitions();
    Optional<Exhibition> getExhibitionByTitle(String title);
    void createExhibition(Exhibition exhibition);
    void updateExhibition(Exhibition exhibition);
    void deleteExhibition(String title);
}