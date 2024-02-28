package edu.esprit.controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AjouterPanier implements Initializable {
    @FXML
     GridPane gridPanier;

    @FXML
    Label nbrProd;

    @FXML
     Label totalP;

    private int nbrProduit = 0;
    private double totalPrix = 0.0;
    ServiceUser serviceUser = new ServiceUser();


    @FXML
    public void passerCommande(ActionEvent actionEvent) {
    }


    @FXML
    void supprimerPanier(ActionEvent event) {
        User loggedInUser = serviceUser.authenticateUser("chams@gamail.com", "chams2000");

        if (loggedInUser != null) {
            servicePanier.supprimerPanierUtilisateur(loggedInUser);
            rafraichirAffichagePanier();
            System.out.println("Le panier a été supprimé avec succès !");
        } else {
            System.out.println("Utilisateur non connecté !");
        }

    }
    ServicePanier servicePanier = new ServicePanier();
    private void afficherProduitsDansPanier() {
        // Effacer le contenu actuel du GridPane
        gridPanier.getChildren().clear();

        User loggedInUser = serviceUser.authenticateUser("chams@gamail.com", "chams2000");

        if (loggedInUser != null) {
            Set<Produit> produitsDansPanier = servicePanier.getProduitsDansPanierUtilisateur(loggedInUser);
            int rowIndex = 0;

            double totalPrix = 0.0;
            nbrProduit = produitsDansPanier.size();

            for (Produit produit : produitsDansPanier) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierList.fxml"));
                    Node produitNode = loader.load();
                    PanierList panierListController = loader.getController();
                    // Mettre à jour les attributs du contrôleur PanierList
                    panierListController.setProduitId(produit.getId_produit());
                    panierListController.setStockPanier(produit.getStock_produit());
                    panierListController.setImagePanier(produit.getImage_produit());
                    panierListController.setNomPanier(produit.getNom_produit());
                    panierListController.setPrixPanier(String.valueOf("Prix : " +produit.getPrix_produit()) + "DT" );



                    panierListController.setAjouterPanierController(this);
                    // Ajouter le PanierList au GridPane
                    gridPanier.add(produitNode, 0, rowIndex++);

                    totalPrix += produit.getPrix_produit();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (nbrProd != null) {
                nbrProd.setText(String.valueOf(nbrProduit));
            }
            if (totalP != null) {
                totalP.setText( "Total : " + String.valueOf(totalPrix) + " DT");
            }
        }

    }


    public void rafraichirAffichagePanier() {
        afficherProduitsDansPanier();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduitsDansPanier();
    }
}
