package edu.esprit.controllers;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PasserCommande {
    @FXML
    private Button btnPanier;

    @FXML
    private Label incrementer1;

    @FXML
    private TextField entrerNom;

    @FXML
    private TextField entrerPrenom;
    @FXML
    private TextField entrerTelephone;


    @FXML
    private TextField entrerMail;

    @FXML
    private TextField entrerLocal;

    @FXML
    private Label alerteNom;

    @FXML
    private Label alertePrenom;

    @FXML
    private Label alerteTel;

    @FXML
    private Label alertMail;

    @FXML
    private Label alertLocal;



    @FXML
    void afficherPanier(ActionEvent event) {

    }

    @FXML
    void annulerComm(ActionEvent event) {

    }

    @FXML
    void marketPlace(ActionEvent event) {

    }

    @FXML
    void passerComm(ActionEvent event) {
        // Initialize empty error messages
        String champsManquants = "";
        alerteNom.setText("");
        alertePrenom.setText("");
        alerteTel.setText("");
        alertMail.setText("");
        alertLocal.setText("");

        // Validate name
        if (entrerNom.getText().length() < 4) {
            entrerNom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alerteNom.setText("Nom invalide (doit contenir au moins 4 caractères)");
            return;
        } else {
            entrerNom.setStyle("");
            alerteNom.setText("");
        }

        // Vérifier la longueur du prénom
        if (entrerPrenom.getText().length() < 4) {
            entrerPrenom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alertePrenom.setText("Prénom invalide (doit contenir au moins 4 caractères)");
            return;
        } else {
            entrerPrenom.setStyle("");
            alertePrenom.setText("");
        }

        // Vérifier le format du numéro de téléphone
        if (!entrerTelephone.getText().matches("^\\d{8}$")) {
            entrerTelephone.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alerteTel.setText("Numéro de téléphone invalide (10 chiffres)");
            return;
        } else {
            entrerTelephone.setStyle("");
            alerteTel.setText("");
        }

        // Vérifier le format de l'email
        if (!entrerMail.getText().matches("^[\\w-_.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            entrerMail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alertMail.setText("Email invalide");
            return;
        } else {
            entrerMail.setStyle("");
            alertMail.setText("");
        }

        // Vérifier si l'adresse de livraison est vide
        if (entrerLocal.getText().isEmpty()) {
            entrerLocal.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            alertLocal.setText("Veuillez entrer une adresse de livraison");
            return;
        } else {
            entrerLocal.setStyle("");
            alertLocal.setText("");
        }


        // Si au moins un champ est manquant, afficher une alerte informant l'utilisateur des champs manquants
        if (!champsManquants.isEmpty()) {
            Alert champsManquantsAlert = new Alert(Alert.AlertType.WARNING);
            champsManquantsAlert.setTitle("Champs manquants");
            champsManquantsAlert.setHeaderText(null);
            champsManquantsAlert.setContentText("Veuillez remplir les champs suivants :\n" + champsManquants);
            champsManquantsAlert.showAndWait();
            return; // Sortir de la méthode car des champs sont manquants
        }



        ServiceUser su = new ServiceUser();
        // Afficher une alerte de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de la commande");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir passer cette commande ?");

        // Attendre la réponse de l'utilisateur
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Si l'utilisateur clique sur "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer les informations du formulaire
            String nom = entrerNom.getText();
            String prenom = entrerPrenom.getText();
            int telephone = Integer.parseInt(entrerTelephone.getText());
            String email = entrerMail.getText();
            String adresseLivraison = entrerLocal.getText();


            User loggedInUser = su.authenticateUser("chams@gmail.com", "chams2000");

            // Créer une instance de ServicePanier
            ServicePanier servicePanier = new ServicePanier();

            // Récupérer tous les paniers de l'utilisateur connecté
            Set<Panier> paniersUtilisateur = servicePanier.getAll().stream()
                    .filter(panier -> panier.getUser().equals(loggedInUser))
                    .collect(Collectors.toSet());

            // Supposons que l'utilisateur n'ait qu'un seul panier
            Panier panier = paniersUtilisateur.stream().findFirst().orElse(null);
            if (panier != null) {
                // Récupérer l'ID du panier
                int idPanier = panier.getId_Panier();

                // Créer une nouvelle commande avec les informations récupérées
                Commande commande = new Commande();
                commande.setNom_client(nom);
                commande.setPrenom_client(prenom);
                commande.setTelephone(telephone);
                commande.setE_mail(email);
                commande.setAdresse_livraison(adresseLivraison);
                commande.setPanier(panier);

                // Ajouter la commande
                ServiceCommande serviceCommande = new ServiceCommande();
                serviceCommande.ajouter(commande);

                // Supprimer le panier après la commande réussie
                servicePanier.supprimerPanierUtilisateur(loggedInUser);


                // Réinitialiser les champs du formulaire
                entrerNom.clear();
                entrerPrenom.clear();
                entrerTelephone.clear();
                entrerMail.clear();
                entrerLocal.clear();

                // Afficher une confirmation à l'utilisateur
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Commande passée");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Votre commande a été passée avec succès !");
                successAlert.showAndWait();
                Stage stage = (Stage) btnPanier.getScene().getWindow();
                stage.close();

                // Charger la fenêtre MarketPlace.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MarketPlace.fxml"));
                    Parent root = loader.load();
                    Stage marketPlaceStage = new Stage();
                    marketPlaceStage.setTitle("MarketPlace");
                    marketPlaceStage.setScene(new Scene(root));
                    marketPlaceStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }


        }
    }


