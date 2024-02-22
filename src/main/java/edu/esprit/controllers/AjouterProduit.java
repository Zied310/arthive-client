package edu.esprit.controllers;

import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.enums.TypeCategorie;
import edu.esprit.services.ServiceProduit;
import edu.esprit.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterProduit implements Initializable {

    @FXML
    private TextField addname;

    @FXML
    private Spinner<Double> priceSpinner;

    @FXML
    private Spinner<Integer> stockSpinner;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView imageview;

    @FXML
    private ImageView arthive;

    @FXML
    private Button annule;


    @FXML
    private ComboBox<TypeCategorie> category;


    @FXML
    private Label userName;

    @FXML
    private ImageView avatar;

    @FXML
    private CheckBox dispo;

    @FXML
    private Button upload;
    @FXML
    private Button add;

    @FXML
    private TextArea description;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Créer une SpinnerValueFactory distincte pour chaque Spinner
        SpinnerValueFactory<Double> priceFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 0.1);
        SpinnerValueFactory<Integer> stockFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);

        // Définir les SpinnerValueFactory pour chaque Spinner
        priceSpinner.setValueFactory(priceFactory);
        stockSpinner.setValueFactory(stockFactory);

        // Remplir le ChoiceBox avec les valeurs de l'énumération TypeCategorie
        ObservableList<TypeCategorie> categories = FXCollections.observableArrayList(TypeCategorie.values());
        category.setItems(categories);
    }

    @FXML
    boolean checkdispo(javafx.event.ActionEvent event) {
        return dispo.isSelected();
    }

    @FXML
    void uploadArt(MouseEvent event) {
        // Créer un sélecteur de fichiers pour les images
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");

        // Filtrer les fichiers pour afficher uniquement les images
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(filter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Charger l'image sélectionnée dans l'interface utilisateur
        if (selectedFile != null) {
            // Vous pouvez implémenter le chargement de l'image dans un ImageView
            Image image = new Image(selectedFile.toURI().toString());
            System.out.println("Chemin de l'image sélectionnée : " + selectedFile.toURI().toString()); // Imprimer le chemin de l'image
            imageview.setImage(image);

            upload.setVisible(false);
        }
    }


    @FXML
    public void canceladd(ActionEvent actionEvent) {
        // Afficher une alerte de confirmation pour confirmer l'annulation de l'ajout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment annuler l'ajout du produit ?");
        Optional<ButtonType> result = alert.showAndWait();

        // Si l'utilisateur clique sur le bouton OK, fermer la fenêtre
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) annule.getScene().getWindow();
            stage.close();
        }
    }


    private ServiceProduit serviceProduit = new ServiceProduit();
    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    void AjouterProduit(ActionEvent event) {
        String nomProduit = addname.getText();
        double prixProduit = priceSpinner.getValue();
        String descriptionProduit = description.getText();
        int stockProduit = stockSpinner.getValue();
        String urlImage = imageview.getImage().getUrl();
        boolean disponibilite = dispo.isSelected();
        TypeCategorie categorieProduit = category.getValue();

        // Initialiser une chaîne pour stocker les champs manquants
        String champsManquants = "";

        // Vérifier chaque champ et ajouter à la chaîne de champs manquants le cas échéant
        if (nomProduit.isEmpty()) {
            champsManquants += "Nom du produit\n";
        }
        if (descriptionProduit.isEmpty()) {
            champsManquants += "Description\n";
        }
        if (urlImage.isEmpty()) {
            champsManquants += "Image\n";
        }
        if (categorieProduit == null) {
            champsManquants += "Catégorie\n";
        }

        // Vérifier si des champs sont manquants
        if (!champsManquants.isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur de remplir tous les champs
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs incomplets");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs suivants :\n" + champsManquants);
            alert.showAndWait();
        } else {
            // Le reste du code pour ajouter le produit comme précédemment
            // Obtenez l'utilisateur actuellement connecté depuis votre service utilisateur
            User loggedInUser = serviceUser.authenticateUser("chams@gamail.com", "chams2000");

            if (loggedInUser != null) {
                // Créer un nouvel objet Produit
                Produit produit = new Produit();
                produit.setUser(loggedInUser); // Associez l'utilisateur actuel au produit
                produit.setNom_produit(nomProduit);
                produit.setPrix_produit(prixProduit);
                produit.setDescription_produit(descriptionProduit);
                produit.setStock_produit(stockProduit);
                produit.setImage_produit(urlImage);
                produit.setDisponibilite(disponibilite);
                produit.setCateg_produit(categorieProduit);
                produit.setD_publication_produit(new Timestamp(System.currentTimeMillis()));

                // Appel de la méthode ajouter de votre service ServiceProduit
                serviceProduit.ajouter(produit);

                // Afficher une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Produit ajouté avec succès!");
                alert.showAndWait();

                // Fermer la fenêtre actuelle
                Stage stage = (Stage) addname.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Erreur: Utilisateur non trouvé.");
            }
        }
    }
}


