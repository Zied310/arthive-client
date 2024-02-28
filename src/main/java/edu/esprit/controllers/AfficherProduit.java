package edu.esprit.controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;



public class AfficherProduit implements Initializable {


    @FXML
    private ImageView art;

    @FXML
    private ImageView avatar;

    @FXML
    private Button add;

    @FXML
    private Button annule;

    @FXML
    private Label imag;

    @FXML
    private GridPane gridList;


    private ServiceUser serviceUser = new ServiceUser();
    private ServiceProduit serviceProduit = new ServiceProduit();

    private AfficherProduit afficherProduitController;
    private ProduitList produitListController;


    public void setAfficherProduitController(AfficherProduit afficherProduitController) {
        this.afficherProduitController = afficherProduitController;
    }

    private MarketPlace marketPlaceController;

    public void setMarketPlaceController(MarketPlace marketPlaceController) {
        this.marketPlaceController = marketPlaceController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherProduitsUtilisateurConnecte();
    }

    public void afficherProduitsUtilisateurConnecte() {
        gridList.getChildren().clear();
        User loggedInUser = serviceUser.authenticateUser("chams@gamail.com", "chams2000");
        if (loggedInUser != null) {
            Set<Produit> produitsUtilisateur = serviceProduit.getProduitsByUser(loggedInUser);
            for (Produit produit : produitsUtilisateur) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProduitList.fxml"));
                    AnchorPane produitNode = loader.load();
                    ProduitList produitController = loader.getController();
                    produitController.setProduitNom("Nom : " + produit.getNom_produit());
                    produitController.setDescriptionProduit("Description : " + produit.getDescription_produit());
                    produitController.setPrixProduit(String.valueOf("Prix : " + produit.getPrix_produit() + " $"));
                    produitController.setStockProduit(String.valueOf("Stock : " + produit.getStock_produit()));
                    produitController.setPublicationTime(produit.getD_publication_produit().toString());
                    Image produitImage = new Image(produit.getImage_produit());
                    produitController.setProduitImage(produitImage);
                    produitController.setProduitId(produit.getId_produit());
                    //refrech AfficherProduit
                    produitController.setAfficherProduitController(this);
                    gridList.add(produitNode, 0, gridList.getRowCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ajoutProd(ActionEvent actionEvent) {
        try {
            // Charger la vue FXML de AjouterProduit
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre (stage)
            Stage stage = new Stage();
            stage.setScene(scene);

            // Afficher la fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void annuler(ActionEvent actionEvent) {
        Node sourceNode = (Node) actionEvent.getSource();
        Scene scene = sourceNode.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void setProduitListController(ProduitList produitListController) {
        this.produitListController = produitListController;
    }

    public void refreshProductList() {
        afficherProduitsUtilisateurConnecte();
        // Rafraîchir la liste des produits dans MarketPlace
        marketPlaceController.refreshProductList();
    }
}





