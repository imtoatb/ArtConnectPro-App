package com.project.artconnect.util;

import com.project.artconnect.service.ArtistService;
import com.project.artconnect.service.ArtworkService;
import com.project.artconnect.service.CommunityService;
import com.project.artconnect.service.ExhibitionService;
import com.project.artconnect.service.GalleryService;
import com.project.artconnect.service.WorkshopService;
import com.project.artconnect.service.impl.InMemoryArtistService;
import com.project.artconnect.service.impl.InMemoryArtworkService;
import com.project.artconnect.service.impl.InMemoryCommunityService;
import com.project.artconnect.service.impl.InMemoryExhibitionService;
import com.project.artconnect.service.impl.InMemoryGalleryService;
import com.project.artconnect.service.impl.InMemoryWorkshopService;

public class ServiceProvider {
    private static final InMemoryArtistService artistService = new InMemoryArtistService();
    private static final InMemoryArtworkService artworkService = new InMemoryArtworkService();
    private static final InMemoryExhibitionService exhibitionService = new InMemoryExhibitionService(); 
    private static final InMemoryGalleryService galleryService = new InMemoryGalleryService();
    private static final InMemoryWorkshopService workshopService = new InMemoryWorkshopService();
    private static final InMemoryCommunityService communityService = new InMemoryCommunityService();

    static {
        artworkService.initData(artistService);
        galleryService.initData(artworkService);  
        workshopService.initData(artistService);
        communityService.initData(artworkService);
    }

    public static ArtistService getArtistService() {
        return artistService;
    }

    public static ArtworkService getArtworkService() {
        return artworkService;
    }

    public static ExhibitionService getExhibitionService() {
        return exhibitionService;   
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