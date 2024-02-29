package edu.esprit.controllers;

import edu.esprit.crud.CrudEvent;
import edu.esprit.crud.ServiceUser;
import edu.esprit.entities.Event;
import edu.esprit.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.paint.Color;


public class AjouterEvent implements Initializable {


    @FXML
    private TextField titreField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField lieuField;

    @FXML
    private Button ajouterEventButton; // Renommé pour éviter la confusion avec le fx:id
    @FXML
    private File selectedFile;
    @FXML
    private ImageView imageView;
    private final CrudEvent crudEvent = new CrudEvent();
    private final ServiceUser serviceUser = new ServiceUser();

    private Timestamp convertDatePickerToTimestamp(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            LocalDateTime localDateTime = datePicker.getValue().atStartOfDay();
            return Timestamp.valueOf(localDateTime);
        }
        return null;
    }

    @FXML
    private void ajouterEvent(ActionEvent event) {
        if (!validateInput()) {
            return; // Validation failed, do not proceed with adding event
        }
        // Récupérez les valeurs des champs
        String titre = titreField.getText();
        Timestamp dateDebut = convertDatePickerToTimestamp(dateDebutPicker);
        Timestamp dateFin = convertDatePickerToTimestamp(dateFinPicker);
        String description = descriptionArea.getText();
        String image = imageView.getImage().getUrl();
        String lieu = lieuField.getText();

        User loggedInUser = serviceUser.authenticateUser("toujnaiayoub808gmail.com", "1234");
        if (loggedInUser != null) {
            //Event eventObj = new Event(titre, dateDebut, dateFin, description, lieu, loggedInUser, "gg.jpg");

            Event eventt = new Event();
            eventt.setUser(loggedInUser); // Associez l'utilisateur actuel au produit
            eventt.setTitre_evenement(titre);
            eventt.setDescription_evenement(description);
            eventt.setLieu_evenement(lieu);
            eventt.setD_debut_evenement(dateDebut);
            eventt.setD_fin_evenement(dateFin);
            eventt.setImage(image);

            crudEvent.ajouter(eventt);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Event added successfully!");
            alert.showAndWait();

            Stage stage = (Stage) titreField.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Error: User not found.");
        }
    }
  //  @FXML
//    void chooseImage(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
//        );
//        selectedFile = fileChooser.showOpenDialog(new Stage());
//
//        if (selectedFile != null) {
//            // Utilisez le chemin absolu du fichier sélectionné
//            String absoluteImagePath = selectedFile.toURI().toString();
//            Image image = new Image(absoluteImagePath);
//
//            // Affichez l'image dans l'ImageView
//            imageView.setImage(image);
//
//            // Vous pouvez afficher le chemin du fichier sélectionné ici si nécessaire
//            System.out.println("Chemin de l'image sélectionnée : " + absoluteImagePath);
//            System.out.println("Selected Image: " + selectedFile.getPath());
//        } else {
//            // User canceled file selection
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Warning");
//            alert.setHeaderText(null);
//            alert.setContentText("Image selection canceled.");
//            alert.showAndWait();
//        }
//    }
    @FXML
    void uploadArt( javafx.scene.input.MouseEvent mouseEvent) {
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
            imageView.setImage(image);

           // upload.setVisible(false);
        }
    }

    private boolean validateInput() {
        if (dateDebutPicker != null && dateDebutPicker.getValue().isBefore(LocalDate.now())) {
            showAlert( "Date de debut invalide.");
            return false;
        }
        // Validation : Assurez-vous que la date de fin n'est pas antérieure à la date de début
        if (dateFinPicker != null && dateDebutPicker != null && dateFinPicker.getValue().isBefore(LocalDate.now())) {
            showAlert("Date de fin invalide.");
            return false;
        }
        // Validate each input field and show appropriate alert if validation fails
        if (titreField.getText().isEmpty() || dateDebutPicker.getValue() == null || dateFinPicker.getValue() == null
                || descriptionArea.getText().isEmpty() || lieuField.getText().isEmpty() || imageView.getImage() == null) {
            showAlert("Veuillez compléter tous les champs.");
            return false;
        }

        if (titreField.getText().length() > 30) {
            showAlert("Le titre ne doit pas dépasser 30 caractères.");
            return false;
        }

        if (descriptionArea.getText().length() > 1000) {
            showAlert("La description ne doit pas dépasser 1000 caractères.");
            return false;
        }
        if (containsDigits(titreField.getText())) {
            showAlert("Le titre ne peut pas contenir de chiffres.");
            return false;
        }

        return true; // All validations passed
    }
    private boolean containsDigits(String s) {
        // Check if the given string contains any digits
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
        
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add event handler to titreField to allow only letters
        titreField.setOnKeyTyped(event -> {
            char inputChar = event.getCharacter().charAt(0);

            // Check if the typed character is a letter
            if (!Character.isLetter(inputChar)) {
                event.consume(); // Consume the event if a non-letter character is typed
            }
        });
        // Add text length validation for titreField
        titreField.setTextFormatter(new TextFormatter<String>((Change c) -> {
            if (c.isAdded() && c.getControlNewText().length() > 30) {
                return null; // Reject the change if length exceeds 15 characters
            }
            return c;
        }));

        // Add text length validation for descriptionArea
        descriptionArea.setTextFormatter(new TextFormatter<String>((Change c) -> {
            if (c.isAdded() && c.getControlNewText().length() > 1000) {
                return null; // Reject the change if length exceeds 1000 characters
            }
            return c;
        }));
    }


}
