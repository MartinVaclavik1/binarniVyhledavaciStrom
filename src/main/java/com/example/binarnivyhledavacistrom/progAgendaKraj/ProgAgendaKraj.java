package com.example.binarnivyhledavacistrom.progAgendaKraj;

import com.example.binarnivyhledavacistrom.Obec;
import com.example.binarnivyhledavacistrom.abstrTable.AbstrTable.Prvek;
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
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Iterator;
import java.util.Optional;

public class ProgAgendaKraj extends Application {
    private final ObservableList<String> observableList = FXCollections.observableArrayList();
    private final ListView<String> listView = new ListView<>(observableList);
    private final String nazevSouboru = "zaloha.bin";
    private IAgendaKraj kraj = new AgendaKraj();
    private final Pane pane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        pane.setPrefSize(1500, 700);
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
        vBox.getChildren().add(newButton("najdi", najdi()));
        vBox.getChildren().add(newButton("odeber", odeber()));
        vBox.getChildren().add(newButton("vybuduj", vybuduj()));
        vBox.getChildren().add(newButton("aktualizuj", aktualizuj()));
        vBox.getChildren().add(newButton("Zobraz strom", vizualizujStrom()));

        Scene scene = new Scene(root);
        stage.setTitle("Václavík - AbstrTable");
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }
    public EventHandler<ActionEvent> vizualizujStrom() {
        return EventHandler -> {
            pane.getChildren().clear();

            double startX = pane.getPrefWidth() / 2;
            double startY = 30;
            double mezera = pane.getPrefWidth() / 4;
            Prvek<String, Obec> koren = kraj.getKoren();

            vizualizujPrvek(koren, startX, startY, mezera);
            dialogStrom();
        };
    }

    private void vizualizujPrvek(Prvek<String, Obec> prvek, double x, double y, double mezera) {
        if (prvek == null) {
            return;
        }

        Text text = new Text(String.valueOf(prvek.getKey()));
        int odchylka = prvek.getKey().length()/2 * 5;
        text.setX(x - odchylka);
        text.setY(y + 5);

        pane.getChildren().add(text);

        double dalsiY = y + 50;
        double levyX = x - mezera;
        double pravyX = x + mezera;

        // Vykreslí čáry k potomkům a pak zavolá sám sebe
        if (prvek.getSynL() != null) {
            Line leftLine = new Line(x, y + 15, levyX, dalsiY - 15);
            pane.getChildren().add(leftLine);
            vizualizujPrvek(prvek.getSynL(), levyX, dalsiY, mezera / 2);
        }

        if (prvek.getSynP() != null) {
            Line rightLine = new Line(x, y + 15, pravyX, dalsiY - 15);
            pane.getChildren().add(rightLine);
            vizualizujPrvek(prvek.getSynP(), pravyX, dalsiY, mezera / 2);
        }
    }
    private void dialogStrom() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(pane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.showAndWait();
    }

    private EventHandler<ActionEvent> najdi() {
        return EventHandler -> {
            try {
                String nazev = dialogNazev();
                if (nazev != null) {
                    if (nazev.isEmpty()) {
                        chybovaHlaska("Nezadán název obce");
                        return;
                    }
                    Obec obec = kraj.najdi(nazev);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(obec.toString());
                    alert.showAndWait();
                }
            } catch (AgendaKrajException e) {
                chybovaHlaska(e.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> odeber() {
        return EventHandler -> {
            try {
                String obec = dialogNazev();
                if (obec != null) {
                    if (obec.isEmpty()) {
                        chybovaHlaska("Nezadán název obce");
                        return;
                    }
                    kraj.odeber(obec);
                    aktualizujListView();
                }
            } catch (AgendaKrajException e) {
                chybovaHlaska(e.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> vybuduj() {
        return EventHandler -> {
            try {
                kraj.vybuduj();
            } catch (AgendaKrajException e) {
                chybovaHlaska(e.getMessage());
            }
        };
    }

    private EventHandler<ActionEvent> aktualizuj() {
        return EventHandler -> aktualizujListView();
    }

    private EventHandler<ActionEvent> nacti() {
        return EventHandler -> {
            try {
                int pocet = 0;
                ObjectInputStream vstup =
                        new ObjectInputStream(
                                new FileInputStream(nazevSouboru));

                //TODO mazat zbytek pole, nebo nechat a přičíst? - pro nechání smazat následující řádek
                this.kraj = new AgendaKraj();

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
                aktualizujListView();
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
            aktualizujListView();
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

                aktualizujListView();
            } catch (AgendaKrajException x) {
                chybovaHlaska(x.getMessage());
            }
        };
    }

    private String dialogNazev() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));

        gridPane.add(new Label("Název obce (Case sensitive):"), 0, 1);

        TextField nazevTXT = new TextField();
        gridPane.add(nazevTXT, 1, 1);

        Dialog<String> dialog = new Dialog<>();


        dialog.getDialogPane().setContent(gridPane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return nazevTXT.getText();
                } catch (Exception x) {
                    chybovaHlaska("Chyba v zadávání názvu obce");
                }
            }
            return null;
        });

        Optional<String> nazev = dialog.showAndWait();
        return nazev.orElse(null);
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
                aktualizujListView();
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

                //int idKraje = Integer.parseInt(rozdelenyRadek[0]);
                int psc = Integer.parseInt(rozdelenyRadek[2]);
                String nazevObce = rozdelenyRadek[3];
                int pocetMuzu = Integer.parseInt(rozdelenyRadek[4]);
                int pocetZen = Integer.parseInt(rozdelenyRadek[5]);


                kraj.vloz(new Obec(psc, nazevObce, pocetMuzu, pocetZen));
                radek = nactenySoubor.readLine();
            }

        } catch (IOException x) {
            chybovaHlaska("Chyba v načítání souboru");
        } catch (AgendaKrajException x) {
            chybovaHlaska(x.getMessage());
        }
    }

    private void aktualizujListView() {

        observableList.clear();

        Iterator<Obec> iterator = kraj.vytvorIterator(eTypProhl.DO_HLOUBKY);
        while (iterator.hasNext()) {
            observableList.addAll(iterator.next().toString());
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
