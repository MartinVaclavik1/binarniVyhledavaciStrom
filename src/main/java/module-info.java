module com.example.binarnivyhledavacistrom {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.binarnivyhledavacistrom to javafx.fxml;
    exports com.example.binarnivyhledavacistrom;
    exports com.example.binarnivyhledavacistrom.FIFO;
    opens com.example.binarnivyhledavacistrom.FIFO to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.LIFO;
    opens com.example.binarnivyhledavacistrom.LIFO to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.enumy;
    opens com.example.binarnivyhledavacistrom.enumy to javafx.fxml;
    exports com.example.binarnivyhledavacistrom.abstrTable;
    opens com.example.binarnivyhledavacistrom.abstrTable to javafx.fxml;
}