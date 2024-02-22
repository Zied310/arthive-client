package edu.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {
    @FXML
    private Button btnMarket;

    @FXML
    private Hyperlink linkMarket;

    @FXML
    void Market(ActionEvent event) {
        openMarketPlace();
    }

    @FXML
    void marketPlace(ActionEvent event) {
        openMarketPlace();
    }

    private void openMarketPlace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
            Parent marketPlaceParent = loader.load();
            Scene marketPlaceScene = new Scene(marketPlaceParent);

            Stage stage = new Stage();
            stage.setScene(marketPlaceScene);
            stage.setTitle("MarketPlace");


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
