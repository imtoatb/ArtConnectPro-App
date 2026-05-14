package com.project.artconnect.util;

import com.project.artconnect.service.*;
import com.project.artconnect.service.impl.*;
import com.project.artconnect.ui.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderBis {
    private static ArtistServiceImpl artistService = new ArtistServiceImpl();
    private static ArtworkServiceImpl artworkService = new ArtworkServiceImpl();
    private static GalleryServiceImpl galleryService = new GalleryServiceImpl();
    private static WorkshopServiceImpl workshopService = new WorkshopServiceImpl();
    private static CommunityServiceImpl communityService = new CommunityServiceImpl();
    private static ExhibitionServiceImpl exhibitionService = new ExhibitionServiceImpl();
    
    private static List<Object> refreshableControllers = new ArrayList<>();

    public static ArtistService getArtistService() { return artistService; }
    public static ArtworkService getArtworkService() { return artworkService; }
    public static GalleryService getGalleryService() { return galleryService; }
    public static WorkshopService getWorkshopService() { return workshopService; }
    public static CommunityService getCommunityService() { return communityService; }
    public static ExhibitionService getExhibitionService() { return exhibitionService; }
    
    public static void registerController(Object controller) {
        if (!refreshableControllers.contains(controller)) {
            refreshableControllers.add(controller);
            System.out.println("Controller registered: " + controller.getClass().getSimpleName());
        }
    }
    
    public static void refreshAllServices() {
        artistService.refreshArtists();
        artworkService.refreshArtworks();
        galleryService.refreshGalleries();
        workshopService.refreshWorkshops();
        exhibitionService.refreshExhibitions();
        System.out.println("All services refreshed");
        
        for (Object controller : refreshableControllers) {
            try {
                if (controller instanceof ArtistController) {
                    ((ArtistController) controller).refresh();
                } else if (controller instanceof ArtworkController) {
                    ((ArtworkController) controller).refresh();
                } else if (controller instanceof WorkshopController) {
                    ((WorkshopController) controller).refresh();
                } else if (controller instanceof GalleryController) {
                    ((GalleryController) controller).refresh();
                } else if (controller instanceof ExhibitionController) {
                    ((ExhibitionController) controller).refresh();
                }
            } catch (Exception e) {
                System.err.println("Failed to refresh: " + e.getMessage());
            }
        }
        System.out.println("All controllers refreshed");
    }
}