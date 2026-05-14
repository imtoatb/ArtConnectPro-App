package com.project.artconnect.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.service.ExhibitionService;
import com.project.artconnect.util.ServiceProvider;

public class InMemoryExhibitionService implements ExhibitionService {
    
    private final InMemoryGalleryService galleryService = (InMemoryGalleryService) ServiceProvider.getGalleryService();

    @Override
    public List<Exhibition> getAllExhibitions() {
        List<Exhibition> all = new ArrayList<>();
        for (Gallery g : galleryService.getAllGalleries()) {
            all.addAll(g.getExhibitions());
        }
        return all;
    }

    @Override
    public Optional<Exhibition> getExhibitionByTitle(String title) {
        return getAllExhibitions().stream()
                .filter(e -> e.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public void createExhibition(Exhibition exhibition) {
        System.out.println("Create exhibition: " + exhibition.getTitle());
    }

    @Override
    public void updateExhibition(Exhibition exhibition) {
        System.out.println("Update exhibition: " + exhibition.getTitle());
    }

    @Override
    public void deleteExhibition(String title) {
        System.out.println("Deleting exhibition: " + title);
        // Parcourir toutes les galeries et supprimer l'exposition
        for (Gallery g : galleryService.getAllGalleries()) {
            Iterator<Exhibition> it = g.getExhibitions().iterator();
            while (it.hasNext()) {
                if (it.next().getTitle().equals(title)) {
                    it.remove();
                    System.out.println("Exhibition deleted from gallery: " + g.getName());
                    return;
                }
            }
        }
        System.out.println("Exhibition not found: " + title);
    }
}