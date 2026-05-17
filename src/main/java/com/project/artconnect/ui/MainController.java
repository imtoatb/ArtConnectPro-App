package com.project.artconnect.ui;

import com.project.artconnect.util.ServiceProviderBis;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.Modality;

import java.io.IOException;

public class MainController {
    @FXML
    private TabPane mainTabPane;
    
    @FXML
    private Tab artistsTab;
    @FXML
    private Tab artworksTab;
    @FXML
    private Tab workshopsTab;
    @FXML
    private Tab galleriesTab;
    @FXML
    private Tab exhibitionsTab;

    @FXML
    public void initialize() {
        // Charger les FXML dans chaque onglet et stocker les contrôleurs
        Platform.runLater(() -> {
            loadTabContent();
            registerControllers();
        });
    }
    
    private void loadTabContent() {
        // Charger le contenu de l'onglet Artists
        loadTab(artistsTab, "ArtistsTab.fxml");
        // Charger le contenu de l'onglet Artworks
        loadTab(artworksTab, "ArtworksTab.fxml");
        // Charger le contenu de l'onglet Workshops
        loadTab(workshopsTab, "WorkshopsTab.fxml");
        // Charger le contenu de l'onglet Galleries
        loadTab(galleriesTab, "GalleriesTab.fxml");
        // Charger le contenu de l'onglet Exhibitions
        loadTab(exhibitionsTab, "ExhibitionsTab.fxml");
    }
    
    private void loadTab(Tab tab, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/artconnect/ui/" + fxmlFile));
            Node content = loader.load();
            tab.setContent(content);
            // Stocker le contrôleur dans userData
            tab.setUserData(loader.getController());
        } catch (IOException e) {
            System.err.println("Failed to load " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void registerControllers() {
        try {
            ArtistController artistController = (ArtistController) artistsTab.getUserData();
            if (artistController != null) {
                ServiceProviderBis.registerController(artistController);
                System.out.println("ArtistController registered");
            }
            
            ArtworkController artworkController = (ArtworkController) artworksTab.getUserData();
            if (artworkController != null) {
                ServiceProviderBis.registerController(artworkController);
                System.out.println("ArtworkController registered");
            }
            
            WorkshopController workshopController = (WorkshopController) workshopsTab.getUserData();
            if (workshopController != null) {
                ServiceProviderBis.registerController(workshopController);
                System.out.println("WorkshopController registered");
            }
            
            GalleryController galleryController = (GalleryController) galleriesTab.getUserData();
            if (galleryController != null) {
                ServiceProviderBis.registerController(galleryController);
                System.out.println("GalleryController registered");
            }
            
            ExhibitionController exhibitionController = (ExhibitionController) exhibitionsTab.getUserData();
            if (exhibitionController != null) {
                ServiceProviderBis.registerController(exhibitionController);
                System.out.println("ExhibitionController registered");
            }
            
        } catch (Exception e) {
            System.err.println("Failed to register controllers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}