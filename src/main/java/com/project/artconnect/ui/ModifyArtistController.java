package com.project.artconnect.ui;

import com.project.artconnect.model.Artist;
import com.project.artconnect.service.ArtistService;
import com.project.artconnect.util.ServiceProviderBis;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyArtistController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField bioField;
    @FXML
    private TextField birthYearField;
    @FXML
    private TextField contactEmailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField websiteField;
    @FXML
    private TextField socialMediaField;

    private final ArtistService artistService = ServiceProviderBis.getArtistService();

    @FXML
    public void initialize() {
        return; // for now, nothing
    }

    public void retrieveArtist(Artist selectedArtist){
        nameField.setText(selectedArtist.getName());
        bioField.setText(selectedArtist.getBio());
        birthYearField.setText(Integer.toString(selectedArtist.getBirthYear()));
        contactEmailField.setText(selectedArtist.getContactEmail());
        phoneField.setText(selectedArtist.getPhone());
        websiteField.setText(selectedArtist.getWebsite());
        socialMediaField.setText(selectedArtist.getSocialMedia());
    }
    public void modifyArtist(){
        Artist myArtist = new Artist();

        myArtist.setName(nameField.getText());
        myArtist.setBio(bioField.getText());
        try {
            int convertedYear = Integer.parseInt(birthYearField.getText());
            myArtist.setBirthYear(convertedYear);
        } catch (Exception e){
            System.out.println("Type Error : Year provided is incorrect for new artist. Year set to 0");
            myArtist.setBirthYear(0);
        }
        myArtist.setContactEmail(contactEmailField.getText());
        myArtist.setPhone(phoneField.getText());
        myArtist.setCity(cityField.getText());
        myArtist.setWebsite(websiteField.getText());
        myArtist.setSocialMedia(socialMediaField.getText());

        try{
            artistService.updateArtist(myArtist);
            System.out.println("Artist modified : " + myArtist.toString());
        } catch (Exception e){
            System.out.println("Error : something messed up when trying to add the artist");
        }

    }

}
