package edu.esprit.controllers;
import edu.esprit.controllers.ProduitPost;
import edu.esprit.entities.Produit;
import edu.esprit.services.ServiceProduit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class MarketPlace implements Initializable {
    @FXML
    private GridPane produitGrid;

    @FXML
    void afficherliste(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnAddP(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ServiceProduit serviceProduit = new ServiceProduit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherProduits();
    }

    private void afficherProduits() {
        Set<Produit> produits = serviceProduit.getAll();

        int rowIndex = 0;
        int colIndex = 0;

        for (Produit produit : produits) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitPost.fxml"));
                AnchorPane produitNode = loader.load();
                ProduitPost produitController = loader.getController();

                // Remplir les données du produit dans le ProduitPost
                produitController.setNomUser(produit.getUser().getNom_user() + " " + produit.getUser().getPrenom_user());
                produitController.setIsmproduit(produit.getNom_produit());
                produitController.setPublicationTime(produit.getD_publication_produit().toString()); // Vous devez formater cette date selon vos besoins
                produitController.setPrixProduit("Prix :" +produit.getPrix_produit() + "$");
                produitController.setStockProduit(produit.getStock_produit() + " en Stock");
                produitController.setDescriptionProduit(produit.getDescription_produit());

                //Charger et définir l'image du produit
                 Image produitImage = new Image( produit.getImage_produit());
                 produitController.setProduitImage(produitImage);

                // Vérifier si le stock est égal à 0 et désactiver le bouton d'achat en conséquence
                if (produit.getStock_produit() == 0) {
                    produitController.getAcheterButton().setDisable(true); // Assurez-vous que le bouton est accessible depuis le contrôleur de ProduitPost
                }



                // Ajouter le ProduitPost au GridPane
                produitGrid.add(produitNode, colIndex, rowIndex);
                // Initialiser le Tooltip
                produitController.initialize();

                // Incrémenter les indices de colonne et de ligne
                colIndex++;
                if (colIndex >= 3) { // Si vous voulez afficher 3 produits par ligne, vous pouvez ajuster cette valeur selon vos besoins
                    colIndex = 0;
                    rowIndex++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}