package edu.esprit.gestiongroupe.controllers;



import edu.esprit.gestiongroupe.entities.Groupe;
import edu.esprit.gestiongroupe.entities.User;
import edu.esprit.gestiongroupe.services.ServiceGroupe;
import edu.esprit.gestiongroupe.services.ServiceUser;
import javafx.fxml.Initializable;

import java.io.IOException;
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

public class ModifierGroupeController {

    @FXML
    private Button ButtonModifier;

    @FXML
    private TextField DescriptionGroupModifier;

    @FXML
    private TextField IdGroupe;

    @FXML
    private TextField NomGroupeUpdate;

    Groupe G = new Groupe();
    ServiceGroupe sv = new ServiceGroupe();
    @FXML
    void UpdateGroupe(ActionEvent event) {
        String id = IdGroupe.getText();
        String nom = NomGroupeUpdate.getText();
        String desc = DescriptionGroupModifier.getText();

        if ( G != null)
        {
            G.setNom_group(nom);
            G.setDescription_group(desc);
            G.setId_group(Integer.parseInt(id));
            G.setId_user(1);
            try {
                sv.modifier(G);
                showAlert("Le groupe a été modifier! ");

            }catch (Exception e)
            {
                e.printStackTrace();
                showAlert("Modification non effectué !");
            }

        }

    }


    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
