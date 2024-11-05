package com.example.binarnivyhledavacistrom.progAgendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTableException;
import com.example.binarnivyhledavacistrom.agendaKraj.AgendaKraj;
import com.example.binarnivyhledavacistrom.agendaKraj.AgendaKrajException;
import com.example.binarnivyhledavacistrom.agendaKraj.IAgendaKraj;
import com.example.binarnivyhledavacistrom.enumy.eTypProhl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class ProgAgendaKraj extends Application {
    private final ObservableList<String> observableList = FXCollections.observableArrayList();
    private final ListView<String> listView = new ListView<>(observableList);
    //    private final Obyvatele obyvatele = new Obyvatele();
    private final String nazevSouboru = "zaloha.bin";
    private IAgendaKraj kraj = new AgendaKraj();

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

        vBox.getChildren().add(newButton("importuj data", importujData()));
        vBox.getChildren().add(newButton("vlož obec", vlozObec()));
        vBox.getChildren().add(newButton("ulož", uloz()));
        vBox.getChildren().add(newButton("načti", nacti()));
        vBox.getChildren().add(newButton("vygeneruj", vygeneruj()));

        Scene scene = new Scene(root);
        stage.setTitle("Václavík - AbstrTable");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }

    private EventHandler<ActionEvent> nacti() {
        return EventHandler -> {
            try {
                int pocet = 0;
//            Objects.requireNonNull(pole);
                ObjectInputStream vstup =
                        new ObjectInputStream(
                                new FileInputStream(nazevSouboru));
                //TODO smazat zbytek pole?
                //this.kraj = new AgendaKraj();

                int konec = vstup.readInt();

                while (konec != -1) {
                    Obec obec = (Obec) vstup.readObject();
                    //System.out.println(obec);
                    if (obec != null) {
                        kraj.vloz(obec);
                        pocet++;
                    }

                    konec = vstup.readInt();

                }
                vstup.close();
                System.out.println("Úspěšně načteno " + pocet + " obcí");

            } catch (Exception x) {
                chybovaHlaska("Chyba při načítání dat");
            }
        };
    }

    private EventHandler<ActionEvent> uloz() {
        return EventHandler -> {
            try {

                ObjectOutputStream vystup =
                        new ObjectOutputStream(
                                new FileOutputStream(nazevSouboru));

                Iterator<Obec> it = kraj.vytvorIterator(eTypProhl.DO_HLOUBKY);

                while (it.hasNext()) {
                    vystup.writeInt(1);
                    vystup.writeObject(it.next());
                }

                vystup.writeInt(-1);

                vystup.close();
                System.out.println("Úspěšně uloženo");
            } catch (Exception x) {
                System.out.println(x.getMessage());
                chybovaHlaska("Chyba při ukládání souboru");
            }
        };
    }

    private EventHandler<ActionEvent> importujData() {
        return EventHandler -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.csv"));
            File soubor = fileChooser.showOpenDialog(new Stage());
            if (soubor != null) {
                nactiData(String.valueOf(soubor));
            }
            //TODO aktualizovat
        };
    }


    private EventHandler<ActionEvent> vlozObec() {
        return EventHandler -> {
            try {
                Obec obec = dialogObec();
                if (obec == null) {
                    System.out.println("obec nebyla zadána");
                    return;
                }
                kraj.vloz(obec);

                //TODO aktualizovat
            } catch (AgendaKrajException x) {
                chybovaHlaska(x.getMessage());
            }
        };
    }

    //vrací buď null, nebo Obec dle zadaných parametrů
    private Obec dialogObec() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.add(new Label("PSČ:"), 0, 2);
        gridPane.add(new Label("Název obce:"), 0, 3);
        gridPane.add(new Label("Počet mužů:"), 0, 4);
        gridPane.add(new Label("Počet žen:"), 0, 5);

        TextField pscTXT = new TextField();
        TextField nazevTXT = new TextField();
        TextField pocetMTXT = new TextField();
        TextField pocetZTXT = new TextField();

        gridPane.add(pscTXT, 1, 2);
        gridPane.add(nazevTXT, 1, 3);
        gridPane.add(pocetMTXT, 1, 4);
        gridPane.add(pocetZTXT, 1, 5);

        Dialog<Obec> dialog = new Dialog<>();


        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    int psc = Integer.parseInt(pscTXT.getText());
                    int pocetM = Integer.parseInt(pocetMTXT.getText());
                    int pocetZ = Integer.parseInt(pocetZTXT.getText());
                    return new Obec(psc, nazevTXT.getText(), pocetM, pocetZ);
                } catch (Exception x) {
                    chybovaHlaska("Chyba v zadávání hodnot. Zadávejte celá čísla");
                }
            }
            return null;
        });

        Optional<Obec> obec = dialog.showAndWait();
        return obec.orElse(null);
    }

    private EventHandler<ActionEvent> vygeneruj() {
        return EventHandler -> {
            try {
                kraj.generuj();

                //TODO aktualizovat
            } catch (AgendaKrajException e) {
                chybovaHlaska(e.getMessage());
            }
        };
    }

    private void nactiData(String soubor) {
        try (BufferedReader nactenySoubor = new BufferedReader(new FileReader((soubor)))) {
            String radek = nactenySoubor.readLine();
            while (radek != null) {
                //0 = id kraje, 1 = ENUM kraj, 2 = PSČ, 3 = obec, 4 = pocet muzu, 5 = pocet zen, 6 = pocet celkem
                String[] rozdelenyRadek = radek.split(";");

                int idKraje = Integer.parseInt(rozdelenyRadek[0]);
                int psc = Integer.parseInt(rozdelenyRadek[2]);
                String nazevObce = rozdelenyRadek[3];
                int pocetMuzu = Integer.parseInt(rozdelenyRadek[4]);
                int pocetZen = Integer.parseInt(rozdelenyRadek[5]);


                kraj.vloz(new Obec(psc, nazevObce, pocetMuzu, pocetZen));
                radek = nactenySoubor.readLine();
            }

            //TODO aktualizovat
        } catch (IOException x) {
            chybovaHlaska("Chyba v načítání souboru");
        } catch (AgendaKrajException x) {
            chybovaHlaska(x.getMessage());
        }
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
