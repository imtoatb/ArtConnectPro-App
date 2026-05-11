package com.project.artconnect.util;

import com.project.artconnect.model.*;
import com.project.artconnect.service.*;
import com.project.artconnect.service.impl.*;

import java.util.List;

public class ServiceProviderBis {
    private static ArtistServiceImpl artistService = new ArtistServiceImpl();
    private static ArtworkServiceImpl artworkService = new ArtworkServiceImpl();
    private static GalleryServiceImpl galleryService = new GalleryServiceImpl();
    private static WorkshopServiceImpl workshopService = new WorkshopServiceImpl();
    private static CommunityServiceImpl communityService = new CommunityServiceImpl();

    static{
        List<Artist> artists = artistService.getAllArtists();
        List<Artwork> artworks = artworkService.getAllArtworks();
        List<Gallery> galleries = galleryService.getAllGalleries();
        List<Workshop> workshops = workshopService.getAllWorkshops();
        List<CommunityMember> community = communityService.getAllMembers();
    }

    public static ArtistService getArtistService() { return artistService; }
    public static ArtworkService getArtworkService() {
        return artworkService;
    }
    public static GalleryService getGalleryService() {
        return galleryService;
    }
    public static WorkshopService getWorkshopService() {
        return workshopService;
    }
    public static CommunityService getCommunityService() {
        return communityService;
    }



}
