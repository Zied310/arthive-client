//package edu.esprit.gestiongroupe.controllers;
//
//import edu.esprit.gestiongroupe.entities.Groupe;
//import edu.esprit.gestiongroupe.services.ServiceGroupe;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.geometry.Insets;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class AfficherGroupeController implements Initializable {
//    private GridPane GroupeGrid;
//
//    private List<Groupe> ListeGroupe;
//
//    private ServiceGroupe SG= new ServiceGroupe();
//
////    public void initialize(URL url, ResourceBundle resourceBundle) {
////        ListeGroupe = new ArrayList<>(data());
////        refreshGrid();
////    }
//
////    public void refreshContent() {
////        ListeGroupe = new ArrayList<>(data());
////        refreshGrid();
////
////    }
//
//    private void refreshGrid() {
//        GroupeGrid.getChildren().clear();
//
//        int columns = 0;
//        int rows = 1; // Start at 1 to avoid the header
//
//        for (int i = 0; i < ListeGroupe.size(); i++) {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/listerPublication.fxml"));
//                VBox postBox = fxmlLoader.load();
//
//                AfficherGroupeController controller = fxmlLoader.getController();
//                //controller.setData(ListeGroupe.get(i));
//
//                if (columns == 3) {
//                    columns = 0;
//                    rows++;
//                }
//                GroupeGrid.add(postBox, columns++, rows);
//                GridPane.setMargin(postBox, new Insets(10));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
////    private List<Groupe> data() {
////        // Fetch data using servicePublication.getAll()
////        //return new ArrayList<>(ServiceGroupe.getAll());
////    }
//
//}
