package com.project.artconnect.ui;

import com.project.artconnect.model.Artwork;
import com.project.artconnect.service.ArtworkService;
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

public class ArtworkController {
    @FXML
    private TableView<Artwork> artworkTable;
    @FXML
    private TableColumn<Artwork, String> titleColumn;
    @FXML
    private TableColumn<Artwork, String> typeColumn;
    @FXML
    private TableColumn<Artwork, Double> priceColumn;
    @FXML
    private TableColumn<Artwork, String> statusColumn;
    @FXML
    private TableColumn<Artwork, String> artistColumn;
    @FXML
    private Button deleteButton;
    @FXML
    private Label statusLabel;

    private final ArtworkService artworkService = ServiceProviderBis.getArtworkService();
    private ObservableList<Artwork> artworkList;  

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        artistColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getArtist() != null ? cellData.getValue().getArtist().getName() : "Unknown"));

        refreshArtworkList();
        
        // Enregistrer ce contrôleur
        ServiceProviderBis.registerController(this);
        
        deleteButton.disableProperty().bind(artworkTable.getSelectionModel().selectedItemProperty().isNull());
    }

    public void handleAdd(){return;}

    public void handleModify(){return;}

    @FXML
    public void handleDelete() {
        Artwork selectedArtwork = artworkTable.getSelectionModel().getSelectedItem();
        
        if (selectedArtwork == null) {
            showAlert("No Selection", "Please select an artwork to delete.", Alert.AlertType.WARNING);
            return;
        }
        
        try {
            artworkService.deleteArtwork(selectedArtwork.getTitle());
            statusLabel.setText("Deleted: " + selectedArtwork.getTitle());
            statusLabel.setStyle("-fx-text-fill: green;");
            refreshArtworkList();
        } catch (Exception e) {
            showAlert("Error", "Failed to delete artwork: " + e.getMessage(), Alert.AlertType.ERROR);
            statusLabel.setText("Error deleting artwork");
            statusLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }
    
    public void refresh() {
        refreshArtworkList();
    }
    
    private void refreshArtworkList() {
        artworkList = FXCollections.observableArrayList(artworkService.getAllArtworks());
        artworkTable.setItems(artworkList);
        System.out.println("Artwork list refreshed, count: " + artworkList.size());
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}