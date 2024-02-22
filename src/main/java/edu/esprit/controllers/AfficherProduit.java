package edu.esprit.controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


public class AfficherProduit  {


    @FXML
    private ImageView art;

    @FXML
    private ImageView avatar;


    @FXML
    private ScrollBar scroll;

    @FXML
    private Button add;

    @FXML
    private Button annule;

    @FXML
    private TextField barreRecherche;

    @FXML
    private Label imag;

    @FXML
    private ListView<String> listeproduit;

    private final ServiceProduit serviceProduit = new ServiceProduit();
    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    public void ajoutProd(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void annule(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void recherche(javafx.event.ActionEvent actionEvent) {
    }


}





