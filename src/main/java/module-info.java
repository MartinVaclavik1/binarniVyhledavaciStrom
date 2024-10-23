module com.example.binarnivyhledavacistrom {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.binarnivyhledavacistrom to javafx.fxml;
    exports com.example.binarnivyhledavacistrom;
}