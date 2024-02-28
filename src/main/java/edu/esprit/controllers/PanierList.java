package edu.esprit.controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PanierList extends Node implements Initializable {

    @FXML
    private ImageView imagePanier;

    @FXML
    private Label nomPanier;

    @FXML
    private Label prixPanier;

    @FXML
    private Spinner<Integer> quantite;
    @FXML
    private Label alerte;

    private AjouterPanier ajouterPanierController; // Ajouter le contrôleur AjouterPanier

    // Méthode pour initialiser la référence au contrôleur AjouterPanier
    public void setAjouterPanierController(AjouterPanier ajouterPanierController) {
        this.ajouterPanierController = ajouterPanierController;
    }
    private int produitId;
    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }
    private int stockPanier ;

    public void setStockPanier(int stockPanier) {
        this.stockPanier = stockPanier;
    }



    public ImageView getImagePanier() {
        return imagePanier;
    }

    public Label getNomPanier() {
        return nomPanier;
    }

    public Label getPrixPanier() {
        return prixPanier;
    }


    public void setImagePanier(String imageUrl) {
        Image image = new Image(imageUrl);
        imagePanier.setImage(image);
    }


    public void setNomPanier(String nom) {
        nomPanier.setText(nom);
    }

    public void setPrixPanier(String prix) {
        prixPanier.setText(prix);
    }
    @FXML
    void supprimer(ActionEvent event) {
        ServiceUser serviceUser = new ServiceUser();
        ServiceProduit sp = new ServiceProduit();
        User loggedInUser = serviceUser.authenticateUser("chams@gamail.com", "chams2000");

        if (loggedInUser != null) {
            ServicePanier servicePanier = new ServicePanier();
            Produit produit = sp.getOneByID(produitId);

            if (produit != null) {
                servicePanier.supprimerProduitDuPanier(loggedInUser, produit);
                // Rafraîchir l'affichage après la suppression
                ajouterPanierController.rafraichirAffichagePanier();
            }
        }
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         SpinnerValueFactory<Integer> stockFactory =new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        quantite.setValueFactory(stockFactory);


        }

    }


