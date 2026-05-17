package com.project.artconnect.ui;

import com.project.artconnect.model.Gallery;
import com.project.artconnect.service.GalleryService;
import com.project.artconnect.util.ServiceProviderBis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class GalleryController {
    @FXML
    private ListView<Gallery> galleryList;

    private final GalleryService galleryService = ServiceProviderBis.getGalleryService();
    private ObservableList<Gallery> galleryObservableList;

    @FXML
    public void initialize() {
        refreshGalleryList();
        
        // Enregistrer ce contrôleur
        ServiceProviderBis.registerController(this);

        galleryList.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Gallery item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " - " + item.getAddress() + " (" + item.getRating() + "/5.0)");
                }
            }
        });
    }

    public void handleAdd(){return;}

    public void handleModify(){return;}

    public void handleDelete(){return;}
    
    public void refresh() {
        refreshGalleryList();
    }
    
    private void refreshGalleryList() {
        galleryObservableList = FXCollections.observableArrayList(galleryService.getAllGalleries());
        galleryList.setItems(galleryObservableList);
        System.out.println("Gallery list refreshed, count: " + galleryObservableList.size());
    }
}