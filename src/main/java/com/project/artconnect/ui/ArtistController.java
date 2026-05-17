package com.project.artconnect.ui;

import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.service.ArtistService;
import com.project.artconnect.util.ServiceProviderBis;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ArtistController {
        public TextField nameField;
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
    private void handleAdd() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddArtistWindow.fxml"));
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add Artist Window");
        primaryStage.setScene(scene);
        // specifies modality of new window

        primaryStage.initModality(Modality.APPLICATION_MODAL);
        // type of window that is forced over the parent (they can't interact with the main app before they close this window)
        // would be a bummer if someone messes with the DB whilst trying to create smtg
        primaryStage.show();
        // opens window
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            System.out.println("Add window closed abruptly");
            primaryStage.close();
        });

        refreshTable();
    }

    public void handleModify() throws IOException {
        // first, gather the selected item
        if (artistTable.getSelectionModel().getSelectedItem() != null) {
            Artist selectedArtist = artistTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyArtistWindow.fxml"));
            Parent root = loader.load();
            // dissociate loader, for communication purpose
            ModifyArtistController controller = loader.getController();
            // get controller of the window to be opened

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Modify Artist Window");
            primaryStage.setScene(scene);
            // specifies modality of new window

            primaryStage.initModality(Modality.APPLICATION_MODAL);
            // type of window that is forced over the parent (they can't interact with the main app before they close this window)
            // would be a bummer if someone messes with the DB whilst trying to create smtg

            controller.retrieveArtist(selectedArtist);

            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                System.out.println("Modify window closed abruptly");
                primaryStage.close();
                refreshTable();
            });
            // opens window
        } else{
            System.out.println("Selection Error : No artist selected, can't modify");
        }

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