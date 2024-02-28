package edu.esprit.controllers;

import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Label descriptProduit;

    @FXML
    private Label nbrStock;

    @FXML
    private Label nomProduit;

    @FXML
    private Label prix;

    @FXML
    private ImageView imageProduit;
    private int produitId;

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }

    private AjouterPanier ajouterPanierController;

    // Méthode pour initialiser la référence au contrôleur AjouterPanier
    public void setAjouterPanierController(AjouterPanier ajouterPanierController) {
        this.ajouterPanierController = ajouterPanierController;
    }




    ServiceProduit sp = new ServiceProduit();


    public void initData(Image image, String userName, String publicationTime, String nomProduit, String prix, String nbrStock, String descriptProduit, int produitId) {
        this.imageProduit.setImage(image);
        this.userName.setText(userName);
        this.publicationTime.setText(publicationTime);
        this.nomProduit.setText(nomProduit);
        this.prix.setText(prix + " Par Pièce");
        this.nbrStock.setText(" Pièce(s) disponible : " + nbrStock);
        this.descriptProduit.setText(descriptProduit);
        this.produitId = produitId;

    }

    @FXML
    public void ajouterPanier(ActionEvent actionEvent) {
        ServiceUser serviceUser = new ServiceUser();
        ServicePanier servicePanier = new ServicePanier();
        User loggedInUser = serviceUser.authenticateUser("chams@gamail.com", "chams2000");

        if (loggedInUser != null) {
            Produit produit = sp.getOneByID(produitId);

            if (produit != null) {
                servicePanier.ajouterAuPanier(loggedInUser, produit);

            }
        }
    }


}


