package com.example.binarnivyhledavacistrom.progAgendaKraj;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;
import java.util.Random;

public class ProgAgendaKraj extends Application {
    private final ObservableList<String> observableList = FXCollections.observableArrayList();
    private final ListView<String> listView = new ListView<>(observableList);
    //    private final Obyvatele obyvatele = new Obyvatele();
    private final String nazevSouboru = "zaloha.bin";
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        listView.setFocusTraversable(false);
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        root.setCenter(listView);
        root.setRight(vBox);
        vBox.setPadding(new Insets(10, 10, 0, 10));
        vBox.setSpacing(5);

//        vBox.getChildren().add(newButton("importuj data", importujData()));
//        vBox.getChildren().add(newButton("vlož obec", vlozObec()));
//        vBox.getChildren().add(newButton("odeber obec", odeberObec()));
//        vBox.getChildren().add(newButton("zpřístupni předchozí", zpristupniPredchozi()));
//        vBox.getChildren().add(newButton("zpřístupni obec", zpristupniObec()));
//        vBox.getChildren().add(newButton("zpřístupni následující", zpristupniNasledujici()));
//        vBox.getChildren().add(newButton("ulož", uloz()));
//        vBox.getChildren().add(newButton("načti", nacti()));
//        vBox.getChildren().add(newButton("vygeneruj", vygeneruj()));
//
//        choiceBox.getItems().addAll(enumKraj.HLAVNIMESTOPRAHA, enumKraj.JIHOCESKY,
//                enumKraj.JIHOMORAVSKY, enumKraj.KARLOVARSKY, enumKraj.VYSOCINA, enumKraj.KRALOVEHRADECKY,
//                enumKraj.LIBERECKY, enumKraj.MORAVSKOSLEZSKY, enumKraj.OLOMOUCKY, enumKraj.PARDUBICKY,
//                enumKraj.PLZENSKY, enumKraj.STREDOCESKY, enumKraj.USTECKY, enumKraj.ZLINSKY);
//
//        choiceBox.getSelectionModel().selectFirst();
//        kraj = choiceBox.getValue();
//        choiceBox.setOnAction(actionEvent -> {
//            if (choiceBox.getSelectionModel().getSelectedItem() != null) {
//                kraj = choiceBox.getSelectionModel().getSelectedItem();
//                aktualizujListView();
//            }
//        });
//        vBox.getChildren().add(choiceBox);

        Scene scene = new Scene(root);
        stage.setTitle("Václavík - AbstrTable");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }

    private Button newButton(String nazev, EventHandler<ActionEvent> handler) {
        Button button = new Button(nazev);
        button.setOnAction(handler);
        button.setPrefWidth(150);
        return button;
    }

    private void chybovaHlaska(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
