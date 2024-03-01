package edu.esprit.gestiongroupe.controllers;

import edu.esprit.gestiongroupe.entities.Groupe;
import edu.esprit.gestiongroupe.entities.User;
import edu.esprit.gestiongroupe.services.ServiceGroupe;
import edu.esprit.gestiongroupe.services.ServiceUser;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreerGroupeController implements Initializable {
    @FXML
    private Button btnCreer;

    @FXML
    private TextArea td_Desc;

    @FXML
    private TextField tf_nom;

    ServiceGroupe sv = new ServiceGroupe();
    @FXML
    void creergroupe(ActionEvent event) {
        String nom = tf_nom.getText();
        String description = td_Desc.getText();
        Groupe G = new Groupe(nom,description,1);
        sv.ajouter(G);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
