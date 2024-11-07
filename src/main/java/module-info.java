module com.example.binarnivyhledavacistrom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.binarnivyhledavacistrom.abstrDoubleList to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.abstrDoubleList;
    exports com.example.binarnivyhledavacistrom.FIFO;
    opens com.example.binarnivyhledavacistrom.FIFO to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.LIFO;
    opens com.example.binarnivyhledavacistrom.LIFO to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.enumy;
    opens com.example.binarnivyhledavacistrom.enumy to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.abstrTable;
    opens com.example.binarnivyhledavacistrom.abstrTable to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.progAgendaKraj;
    opens com.example.binarnivyhledavacistrom.progAgendaKraj to javafx.fxml;
}