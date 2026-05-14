package com.project.artconnect.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.service.ExhibitionService;
import com.project.artconnect.service.GalleryService;
import com.project.artconnect.util.ServiceProviderBis;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExhibitionController {
    @FXML
    private TableView<Exhibition> exhibitionTable;
    @FXML
    private TableColumn<Exhibition, String> titleColumn;
    @FXML
    private TableColumn<Exhibition, LocalDate> dateColumn;
    @FXML
    private TableColumn<Exhibition, String> themeColumn;
    @FXML
    private TableColumn<Exhibition, String> galleryColumn;
    @FXML
    private Button deleteButton;  
    @FXML
    private Label statusLabel;    

    private final GalleryService galleryService = ServiceProviderBis.getGalleryService();
    private final ExhibitionService exhibitionService = ServiceProviderBis.getExhibitionService();
    private ObservableList<Exhibition> exhibitionList;  

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        themeColumn.setCellValueFactory(new PropertyValueFactory<>("theme"));

        galleryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getGallery() != null ? cellData.getValue().getGallery().getName() : "Unknown"));

        if (deleteButton != null) {
            deleteButton.disableProperty().bind(exhibitionTable.getSelectionModel().selectedItemProperty().isNull());
        }
        
        refreshData();
        
        // Enregistrer ce contrôleur
        ServiceProviderBis.registerController(this);
    }

    @FXML
    private void handleDeleteExhibition() {
        Exhibition selectedExhibition = exhibitionTable.getSelectionModel().getSelectedItem();
        
        if (selectedExhibition == null) {
            showAlert("No Selection", "Please select an exhibition to delete.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            exhibitionService.deleteExhibition(selectedExhibition.getTitle());
            if (statusLabel != null) {
                statusLabel.setText("Deleted: " + selectedExhibition.getTitle());
                statusLabel.setStyle("-fx-text-fill: green;");
            }
            refreshData();
        } catch (Exception e) {
            showAlert("Error", "Failed to delete exhibition: " + e.getMessage(), Alert.AlertType.ERROR);
            if (statusLabel != null) {
                statusLabel.setText("Error deleting exhibition");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
            e.printStackTrace();
        }
    }

    private void refreshData() {
        List<Exhibition> all = new ArrayList<>();
        for (Gallery g : galleryService.getAllGalleries()) {
            all.addAll(g.getExhibitions());
        }
        exhibitionList = FXCollections.observableArrayList(all);
        exhibitionTable.setItems(exhibitionList);
        System.out.println("Exhibition list refreshed, count: " + exhibitionList.size());
    }
    
    public void refresh() {
        refreshData();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}