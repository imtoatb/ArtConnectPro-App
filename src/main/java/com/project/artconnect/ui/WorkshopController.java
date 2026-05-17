package com.project.artconnect.ui;

import com.project.artconnect.model.Workshop;
import com.project.artconnect.service.WorkshopService;
import com.project.artconnect.util.ServiceProviderBis;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDateTime;

public class WorkshopController {
    @FXML
    private TableView<Workshop> workshopTable;
    @FXML
    private TableColumn<Workshop, String> titleColumn;
    @FXML
    private TableColumn<Workshop, LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Workshop, String> instructorColumn;
    @FXML
    private TableColumn<Workshop, Double> priceColumn;
    @FXML
    private TableColumn<Workshop, String> levelColumn;

    private final WorkshopService workshopService = ServiceProviderBis.getWorkshopService();
    private ObservableList<Workshop> workshopList;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        instructorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getInstructor() != null ? cellData.getValue().getInstructor().getName()
                        : "Unknown"));

        refreshWorkshopList();
        
        // Enregistrer ce contrôleur
        ServiceProviderBis.registerController(this);
    }

    public void handleAdd(){return;}

    public void handleModify(){return;}

    public void handleDelete(){return;}

    public void refresh() {
        refreshWorkshopList();
    }
    
    private void refreshWorkshopList() {
        workshopList = FXCollections.observableArrayList(workshopService.getAllWorkshops());
        workshopTable.setItems(workshopList);
        System.out.println("Workshop list refreshed, count: " + (workshopList != null ? workshopList.size() : 0));
    }
}