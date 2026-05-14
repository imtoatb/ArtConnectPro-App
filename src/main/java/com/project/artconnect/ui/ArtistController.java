package com.project.artconnect.ui;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.service.ArtistService;
import com.project.artconnect.util.ServiceProviderBis;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ArtistController {
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<Discipline> disciplineFilter;
    @FXML
    private TableView<Artist> artistTable;
    @FXML
    private TableColumn<Artist, String> nameColumn;
    @FXML
    private TableColumn<Artist, String> cityColumn;
    @FXML
    private TableColumn<Artist, String> emailColumn;
    @FXML
    private TableColumn<Artist, Integer> yearColumn;

    private final ArtistService artistService = ServiceProviderBis.getArtistService();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("birthYear"));

        refreshDisciplines();
        refreshTable();
        
        // Enregistrer ce contrôleur
        ServiceProviderBis.registerController(this);
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        Discipline d = disciplineFilter.getValue();
        String dName = (d != null) ? d.getName() : null;
        artistTable.setItems(FXCollections.observableArrayList(
                artistService.searchArtists(query, dName, null)
        ));
    }

    @FXML
    private void handleReset() {
        searchField.clear();
        disciplineFilter.setValue(null);
        refreshTable();
    }

    @FXML
    private void handleDelete() {
        Artist selectedArtist = artistTable.getSelectionModel().getSelectedItem();
        
        if (selectedArtist == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an artist to delete.");
            alert.showAndWait();
            return;
        }
        
        try {
            artistService.deleteArtist(selectedArtist.getName());
            
            // Rafraîchir tous les services
            ServiceProviderBis.refreshAllServices();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Artist \"" + selectedArtist.getName() + "\" has been deleted.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Failed");
            alert.setHeaderText(null);
            alert.setContentText("Could not delete artist: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        List<Artist> artists = artistService.getAllArtists();
        System.out.println("Refreshing table with " + artists.size() + " artists");
        artistTable.setItems(FXCollections.observableArrayList(artists));
    }
    
    private void refreshDisciplines() {
        List<Discipline> disciplines = artistService.getAllDisciplines();
        disciplineFilter.setItems(FXCollections.observableArrayList(disciplines));
    }
    
    public void refresh() {
        refreshTable();
        refreshDisciplines();
    }
}