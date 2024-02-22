package edu.esprit.controllers;

import edu.esprit.entities.Produit;
import edu.esprit.services.ServiceProduit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;
import java.util.Set;

public class AcheterProduit {
    @FXML
    private ImageView art;

    @FXML
    private ImageView avatar;

    @FXML
    private Label userName;

    @FXML
    private Label publicationTime;

    @FXML
    private ImageView imageProduit;

    @FXML
    private Label nomProduit;

    @FXML
    private Label descriptProduit;

    @FXML
    private Label prix;

    @FXML
    private Label nbrStock;


    ServiceProduit sp = new ServiceProduit();


    public void initData(Image image, String userName, String publicationTime, String nomProduit, String prix, String nbrStock, String descriptProduit) {
        this.imageProduit.setImage(image);
        this.userName.setText(userName);
        this.publicationTime.setText(publicationTime);
        this.nomProduit.setText(nomProduit);
        this.prix.setText(prix + " Par Pièce");
        this.nbrStock.setText(" Pièce(s) disponible : " + nbrStock);
        this.descriptProduit.setText(descriptProduit);

    }
}


