package edu.esprit.controllers;
import edu.esprit.entities.Event;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;


public class VoirDetail implements Initializable {
    @FXML
    private Label titreLabel;
    @FXML
    private Label descriptionText;
    @FXML
    private ImageView eventImageView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label dateText;

    @FXML
    private Label lieuText;

     public void initializeDetails(Event evenement) {
         if (evenement != null) {
             titreLabel.setText(evenement.getTitre_evenement());
             descriptionText.setText(evenement.getDescription_evenement());
             lieuText.setText(evenement.getLieu_evenement());
             dateText.setText(evenement.getD_debut_evenement().toString());

         } else {
             // Gérer le cas où evenement est null, par exemple en effaçant le texte
             titreLabel.setText("");
             descriptionText.setText("");
             lieuText.setText("");
             dateText.setText("");

         }

}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Laisser le corps de la méthode vide si aucune initialisation supplémentaire n'est nécessaire.
    }
   
    }


