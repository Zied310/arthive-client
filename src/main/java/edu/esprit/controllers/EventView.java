package edu.esprit.controllers;

import edu.esprit.crud.CrudEvent;
import edu.esprit.crud.CrudParticipations;
import edu.esprit.crud.ServiceUser;
import edu.esprit.entities.Event;

import edu.esprit.entities.Participation;
import edu.esprit.entities.User;
import javafx.application.Platform;
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
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;


public class EventView extends AnchorPane implements Initializable {
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;


    @FXML
    private AnchorPane eventViewAnchorPane;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Label titleText;

    @FXML
    private Label dateText;

    @FXML
    private Label lieuText;
    @FXML
    private Label ParticipantText;

    @FXML
    private Hyperlink voirDetail;
    @FXML
    private Button participerButton;
    @FXML
    private Label descriptionText;


    @FXML
    private TextField titreTextField;

    @FXML
   private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField lieuField;
    private AfficherEvent afficherEvent;



    public TextField getTitreTextField() {
        return titreTextField;
    }

    public void setTitreTextField(TextField titreTextField) {
        this.titreTextField = titreTextField;
    }

    public DatePicker getDateDebutPicker() {
        return dateDebutPicker;
    }

    public void setDateDebutPicker(DatePicker dateDebutPicker) {
        this.dateDebutPicker = dateDebutPicker;
    }

    public DatePicker getDateFinPicker() {
        return dateFinPicker;
    }

    public void setDateFinPicker(DatePicker dateFinPicker) {
        this.dateFinPicker = dateFinPicker;
    }

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public void setDescriptionArea(TextArea descriptionArea) {
        this.descriptionArea = descriptionArea;
    }

    public TextField getLieuField() {
        return lieuField;
    }

    public void setLieuField(TextField lieuField) {
        this.lieuField = lieuField;
    }

    public Button getModifierButton() {
        return modifierButton;
    }

    public void setModifierButton( Button modifierButton) {
        this.modifierButton = modifierButton;
    }

    public Button getSupprimerButton() {
        return supprimerButton;
    }

    public void setSupprimerButton( Button supprimerButton) {
        this.supprimerButton = supprimerButton;
    }

    public Button getParticiperButton() {
        return participerButton;
    }

    public void setParticiperButton( Button ParticiperButton) {
        this.participerButton = ParticiperButton;
    }


    public ImageView getImage() {
        return eventImageView;
    }

    public void setImage(ImageView image) {
        this.eventImageView = image;
    }
    public Hyperlink getVoirDetail() {
        return voirDetail;
    }

    public void setVoirDetail(ImageView image) {
        this.voirDetail = voirDetail;
    }

    public ImageView getEventImageView() {
        return eventImageView;
    }

    public void setEventImageView(Image image) {
        eventImageView.setImage(image);
    }

    public Label getTitleText() {
        return titleText;
    }

    public void setTitleText(String title) {
        titleText.setText(title);
    }

    public Label getDateText() {
        return dateText;
    }

    public void setDateText(String date) {
        dateText.setText(date);
    }

    public Label getLieuText() {
        return lieuText;
    }

    public void setLieuText(String lieu) {
        lieuText.setText(lieu);
    }

    public Label getParticipantText() {
        return ParticipantText;
    }

    public void setParticipantText(Label participantText) {
        ParticipantText = participantText;
    }

    public Button getViewDetailsButton() {
        return participerButton;
    }
    public void setDescriptionText(String description) {
        descriptionText.setText(description);
    }
    public void setAfficherEvent(AfficherEvent afficherEvent) {
        this.afficherEvent = afficherEvent;
    }

   // private Event evenement;
   private Event evenement;

    public void modifierButtonClicked(ActionEvent event) {

        Button source = (Button) event.getSource();

        // Assurez-vous que userData est de type Event
        Object userData = source.getUserData();

        if (userData instanceof Event) {
            Event evenement = (Event) userData;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
                Parent root = loader.load();

                ModifierEvent modifierController = loader.getController();
                modifierController.initData(evenement);
                modifierController.initEventView(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }
    }


    public void refreshEventDetails(Event updatedEvent) {
        // Mettez à jour les éléments FXML avec les nouveaux détails de l'événement
        titleText.setText("Titre: " + updatedEvent.getTitre_evenement());

        // Assurez-vous que la date de début est non null avant de l'afficher
        if (updatedEvent.getD_debut_evenement() != null) {
            dateText.setText(updatedEvent.getD_debut_evenement().toString());
        } else {
            dateText.setText("Date non spécifiée");
        }

        lieuText.setText("Lieu: " + updatedEvent.getLieu_evenement());

        // Assurez-vous que la description est non null avant de l'afficher
        if (updatedEvent.getDescription_evenement() != null) {
            descriptionText.setText(updatedEvent.getDescription_evenement());
        } else {
            descriptionText.setText("Aucune description disponible");
        }


        // Vous devrez ajouter des mises à jour pour d'autres détails en fonction de votre classe Event
        System.out.println("Mise à jour des détails de l'événement : " + updatedEvent);

    }



    public void supprimerButtonClicked(ActionEvent event) {
        CrudEvent crudEvent = new CrudEvent();

        Button source = (Button) event.getSource();
        Object userData = source.getUserData();
        if (userData instanceof Event) {
            Event evenement = (Event) userData;
        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet événement ?");
        alert.setContentText("Cliquez sur OK pour confirmer la suppression, ou Annuler pour annuler.");
        Optional<ButtonType> result = alert.showAndWait();

        if ( result.isPresent() && result.get() == ButtonType.OK) {

            // Appel à la méthode de suppression du CrudEvent
            crudEvent.supprimer(evenement);

            // Rafraîchissez ou mettez à jour l'interface utilisateur avec la nouvelle liste d'événements
           // refreshEventDetails(evenement);
           // afficherEvent.refreshEventDetails(evenement);
            // Rafraîchissez la liste des événements après la suppression
            List<Event> updatedEventList = crudEvent.getAll();  // ou toute autre méthode pour obtenir la liste mise à jour
            refreshEventList(updatedEventList);

            } else {
                System.out.println("Failed to delete event with ID " + evenement.getId_event());
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }

        }

    private int participantId;
    private CrudParticipations crudParticipations = new CrudParticipations();

    public void participerButton(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();

        // Assurez-vous que le userData est correctement défini sur le bouton
        Object userData = sourceButton.getUserData();

        if (userData instanceof Event) {
            ServiceUser serviceUser = new ServiceUser();
            Event evenement = (Event) userData;  // Récupérez l'événement à partir du userData

            // Utilisez l'ID de l'utilisateur connecté comme participantId
            User loggedInUser = serviceUser.authenticateUser("toujnaiayoub808gmail.com", "1234");
            int participantId = loggedInUser.getId_user();

            //crudParticipations.ajouter(new Participation(participantId, evenement.getId_event()));
            boolean isParticipating = crudParticipations.isParticipant(participantId, evenement.getId_event());
            if (isParticipating) {
                // Si l'utilisateur participe déjà, supprimez la participation
                crudParticipations.supprimer(new Participation(participantId, evenement.getId_event()));
                evenement.removeParticipant();  // Mettez à jour immédiatement le nombre de participants
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Participation supprimée");
                alert.setHeaderText(null);
                alert.setContentText("Votre participation à cet événement a été supprimée.");
                alert.showAndWait();
            } else {
                // Si l'utilisateur ne participe pas, ajoutez la participation
                crudParticipations.ajouter(new Participation(participantId, evenement.getId_event()));
                evenement.addParticipant();  // Mettez à jour immédiatement le nombre de participants
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Vous avez participé");
                alert.setHeaderText(null);
                alert.setContentText("Réservez la date...!");
                alert.showAndWait();
            }

            updateParticipantCount(evenement);
        } else {
            System.out.println("UserData n'est pas une instance de Event. Vérifiez votre configuration.");
        }
    }

    public void voirDetailClicked(ActionEvent event) {
        Hyperlink source = (Hyperlink) event.getSource();

        // Assurez-vous que userData est de type Event
        Object userData = source.getUserData();

        if (userData instanceof Event) {
            Event evenement = (Event) userData;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/voirDetail.fxml"));
                Parent root = loader.load();

                VoirDetail detailsController = loader.getController();

                // Utilisez la classe EventDetailsController pour initialiser les détails
                detailsController.initializeDetails(evenement);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UserData is not an instance of Event. Check your setup.");
        }
    }
    private void updateParticipantCount(Event evenement) {
        boolean isParticipating = crudParticipations.isParticipant(participantId, evenement.getId_event());

        // Obtenez le nombre de participants pour l'événement
        int currentNumberOfParticipants = crudParticipations.getParticipantsForEvent(evenement.getId_event()).size();
        // Vérifiez si l'utilisateur participe actuellement ou non
        if (isParticipating) {
            // Si l'utilisateur participe, mettez à jour le nombre total de participants en le réduisant de 1
            int newNumberOfParticipants = currentNumberOfParticipants - 1;
            // Mettez à jour l'interface utilisateur
            Platform.runLater(() -> {
                ParticipantText.setText(newNumberOfParticipants + " participants");
            });
        } else {
            // Si l'utilisateur ne participe pas, mettez à jour le nombre total de participants en ajoutant 1
            int newNumberOfParticipants = currentNumberOfParticipants + 1;
            // Mettez à jour l'interface utilisateur
            Platform.runLater(() -> {
                ParticipantText.setText(newNumberOfParticipants + " participants");
            });
        }
    }

    private CrudEvent crudEvent = new CrudEvent();

    public void initializeEvent(Event evenement, double imageWidth, double imageHeight) {
        ImageView eventImageView = new ImageView();

        eventImageView.setFitWidth(50);
        eventImageView.setFitHeight(50);

            if (evenement != null) {
                // Assurez-vous que la propriété "imagePath" de la classe Event correspond à votre chemin d'image
                String imagePath = evenement.getImage();

                // Chargez et définissez l'image
                if (imagePath != null && !imagePath.isEmpty()) {
                    Image image = new Image(new File(imagePath).toURI().toString());
                    eventImageView.setImage(image);
                }
            }

            titleText.setText( evenement.getTitre_evenement());
            dateText.setText(evenement.getD_debut_evenement().toString());
            lieuText.setText( evenement.getLieu_evenement());
            Set<Participation> participations = crudEvent.getParticipationsForEvent(evenement.getId_event());
        if (participations != null) {
            ParticipantText.setText( participations.size() +" "+ "paticipants");
        } else {
            ParticipantText.setText("La liste des participants est nulle.");
        }

            voirDetail.setUserData(evenement);

        // Ajoutez le code ici pour définir le userData du bouton "Modifier"
        modifierButton.setUserData(evenement);
        modifierButton.setOnAction(this::modifierButtonClicked);
        supprimerButton.setUserData(evenement);
        supprimerButton.setOnAction(this::supprimerButtonClicked);
        participerButton.setUserData(evenement);
        participerButton.setOnAction(this::participerButton);


        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       descriptionText = new Label();
        supprimerButton.setOnAction(this::supprimerButtonClicked);

    }
//    public void initializeEventView(ObservableList<Event> eventsList) {
//        // Configurez la vue avec la liste observable
//        // ...
//    }
    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }
    public void refreshEventList(List<Event> updatedEventList) {
        // Mettez à jour la liste des événements avec la nouvelle liste
        // Mettez à jour l'interface utilisateur en conséquence
        // ...
    }


}

