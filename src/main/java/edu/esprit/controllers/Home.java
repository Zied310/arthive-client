package edu.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

 @FXML
private Button btnPanier;

@FXML
private Hyperlink linkMarket;

@FXML
private Label incrementer;

@FXML
void afficherPanier(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPanier.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
void marketPlace(ActionEvent event) {

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("MarketPlace");

        stage.show();
        Stage homeStage = (Stage) linkMarket.getScene().getWindow();
        homeStage.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
}