package edu.esprit.gestiongroupe.controllers;

import edu.esprit.gestiongroupe.entities.Groupe;
import edu.esprit.gestiongroupe.services.ServiceGroupe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SupprimerGroupeController {
    @FXML
    private TextField idGroupeSupprimer;
    ServiceGroupe SG = new ServiceGroupe();


    @FXML
    void SupprimerGroupe(ActionEvent event) {
        int id = Integer.parseInt(idGroupeSupprimer.getText());


        try {
            SG.supprimer(id);
            showAlert("Le groupe a été supprimer! ");

        }catch (Exception e)
        {
            e.printStackTrace();
            showAlert("Modification non effectué !");
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


