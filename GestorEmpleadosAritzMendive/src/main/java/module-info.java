module com.example.gestorempleadosaritzmendive {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.gestorempleadosaritzmendive to javafx.fxml;
    exports com.example.gestorempleadosaritzmendive;
    exports Modelo;
    opens Modelo to javafx.fxml;
}