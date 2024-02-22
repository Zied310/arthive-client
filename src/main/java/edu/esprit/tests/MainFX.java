package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));

        // Créer une scène
        Scene scene = new Scene(root);

        // Définir la scène sur la fenêtre principale (primaryStage)
        primaryStage.setScene(scene);

        // Définir le titre de la fenêtre principale
        primaryStage.setTitle("ArtHive");

        // Afficher la fenêtre principale
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}